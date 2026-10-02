package com.asotec.compliance.service;

import com.asotec.compliance.dao.SegLogAccesoDao;
import com.asotec.compliance.dao.SegOtpDao;
import com.asotec.compliance.dao.SegPoliticaDao;
import com.asotec.compliance.dao.SegSesionDao;
import com.asotec.compliance.dao.SegUsuarioDao;
import com.asotec.compliance.dto.PoliticaPasswordResponse;
import com.asotec.compliance.entity.SegUsuario;
import com.asotec.compliance.entity.SegOtp;
import com.asotec.compliance.entity.SegSesion;
import com.asotec.compliance.entity.SegPolitica;
import com.asotec.compliance.entity.SegLogAcceso;
import com.asotec.compliance.entity.ApiResponse;
import com.asotec.compliance.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private SegUsuarioDao usuarioDao;

    @Autowired
    private SegPoliticaDao segPoliticaDao;

    @Autowired
    private SegLogAccesoDao logAccesoDao;

    @Autowired
    private SegOtpDao segOtpDao;
    @Autowired
    private SegSesionDao segSesionDao;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ApiResponse<Object> procesarLogin(String login, String clave, String deviceId, String ip, String userAgent) {

        String loginInput = login;
        SegUsuario usuario = usuarioDao.findByNomLoginIgnoreCase(loginInput);

        // 1. El usuario no existe (Mitigación de timing attacks con BCrypt falso)[cite: 7]
        if (usuario == null) {
            passwordEncoder.matches(clave, "$2a$10$falsoHashGenericoParaEquipararTiempoDeRespuesta123");
            registrarLog(loginInput, null, null, "LOGIN", "USUARIO_NO_EXISTE", ip, deviceId, userAgent, "N");
            return ApiResponse.error("CREDENCIALES_INVALIDAS");
        }

        SegPolitica politica = segPoliticaDao.findByCodInstitucion(usuario.getCodInstitucion())
                .orElseGet(() -> null);

        // 2. Bloqueo temporal vigente[cite: 7]
        boolean bloqueoVigente = usuario.getFecBloqueoHasta() != null && usuario.getFecBloqueoHasta().isAfter(LocalDateTime.now());
        if (bloqueoVigente) {
            registrarLog(loginInput, usuario.getCodUsuario(), null, "BLOQUEO", "USUARIO_BLOQUEADO", ip, deviceId, userAgent, "N");
            return ApiResponse.error("USUARIO_BLOQUEADO");
        }

        // 3. Contraseña incorrecta[cite: 7]
        if (!passwordEncoder.matches(clave, usuario.getTxtHashClave())) {
            usuarioDao.registrarIntentoFallido(usuario.getCodUsuario(), politica.getNumIntentosMax(), politica.getNumMinBloqueo());
            registrarLog(loginInput, usuario.getCodUsuario(), null, "LOGIN", "CLAVE_INCORRECTA", ip, deviceId, userAgent, "N");
            return ApiResponse.error("CREDENCIALES_INVALIDAS");
        }

        // 4. Contraseña correcta: reinicio de intentos fallidos[cite: 7]
        usuarioDao.reiniciarIntentosFallidos(usuario.getCodUsuario());

        // 5. Usuario inactivo o bloqueado por el administrador[cite: 7]
        if ("I".equals(usuario.getStsUsuario()) || "B".equals(usuario.getStsUsuario())) {
            registrarLog(loginInput, usuario.getCodUsuario(), null, "LOGIN", "USUARIO_INACTIVO", ip, deviceId, userAgent, "N");
            return ApiResponse.error("USUARIO_INACTIVO");
        }

        // 5.5. Verificación de Contraseña Vencida o Cambio Obligatorio (Paso 2.5)[cite: 7, 8]
        boolean claveVencida = false;
        if (usuario.getFecCambioClave() != null && politica.getNumClaveDiasVigencia() != null) {
            LocalDateTime fechaVencimiento = usuario.getFecCambioClave().plusDays(politica.getNumClaveDiasVigencia());
            claveVencida = fechaVencimiento.isBefore(LocalDateTime.now());
        }
        boolean cambioObligatorio = "S".equalsIgnoreCase(usuario.getStsCambioObligatorio());

        if (claveVencida || cambioObligatorio) {
            Map<String, Object> tokenCambio = jwtUtil.generarTokenCambio(usuario.getCodUsuario());
            registrarLog(loginInput, usuario.getCodUsuario(), null, claveVencida ? "CLAVE_VENCIDA" : "CAMBIO_OBLIGATORIO", "CAMBIO_REQUERIDO", ip, deviceId, userAgent, "N");
            return ApiResponse.success(tokenCambio, "CAMBIO_REQUERIDO");
        }

        // 6. Segundo factor (MFA) - Si es obligatorio, se emite Token de Desafío[cite: 7, 8]
        boolean mfaObligatorio = "S".equalsIgnoreCase(politica.getStsMfaObligatorio()) || "S".equalsIgnoreCase(usuario.getStsMfa());
        if (mfaObligatorio) {
            if (usuario.getNumCelular() == null || usuario.getNumCelular().isEmpty()) {
                registrarLog(loginInput, usuario.getCodUsuario(), null, "OTP", "MFA_SIN_CELULAR", ip, deviceId, userAgent, "N");
                return ApiResponse.error("MFA_SIN_CELULAR: No tiene celular registrado. Contacte al administrador.");
            }

            // 1. Anular códigos pendientes anteriores[cite: 8]
            segOtpDao.anularOtpsPendientes(usuario.getCodUsuario());

            // 2. Generar 6 dígitos con SecureRandom[cite: 8]
            int codigoInt = 100000 + new java.security.SecureRandom().nextInt(900000);
            String codigoPlano = String.valueOf(codigoInt);
            String hashCodigo = passwordEncoder.encode(codigoPlano);

            // 3. Máscara del celular (*******4567)[cite: 8]
            String celular = usuario.getNumCelular();
            String destinoMascara = celular.length() > 4 ? "*".repeat(celular.length() - 4) + celular.substring(celular.length() - 4) : "****";

            long segundosVigencia = politica.getNumOtpSegVigencia() != null ? politica.getNumOtpSegVigencia() : 300L;

            // 4. Guardar en seg_otp[cite: 8]
            SegOtp otp = new SegOtp();
            otp.setCodUsuario(usuario.getCodUsuario());
            otp.setCodProposito("LOGIN");
            otp.setTxtHashCodigo(hashCodigo);
            otp.setTxtCanal("SMS");
            otp.setTxtDestinoMask(destinoMascara);
            otp.setFecGenerado(LocalDateTime.now());
            otp.setFecExpira(LocalDateTime.now().plusSeconds(segundosVigencia));
            otp.setNumIntentos(0);
            otp.setNumReenvios(0);
            otp.setStsOtp("P");
            segOtpDao.save(otp);

            // 5. Enviar por consola (futuro SMS)[cite: 8]
            notificacionService.enviarCodigoOtp(destinoMascara, codigoPlano);

            // 6. Emitir Token de Desafío[cite: 8]
            Map<String, Object> tokenDesafio = jwtUtil.generarTokenDesafio(usuario.getCodUsuario(), otp.getNumOtp(), segundosVigencia);
            registrarLog(loginInput, usuario.getCodUsuario(), null, "OTP", "OTP_REQUERIDO", ip, deviceId, userAgent, "N");

            return ApiResponse.success(tokenDesafio, "OTP_REQUERIDO");
        }

        // 7. Si no hay MFA, se procede directamente a la creación de sesión y emisión de Token de Sesión[cite: 2, 7, 10]
        UUID idSesion = UUID.randomUUID();
        Date fecInicio = new Date();
        Date fecExpira = new Date(fecInicio.getTime() + (politica.getNumMinSesionMax() * 60L * 1000L));

        Map<String, Object> tokenSesion = jwtUtil.generarTokenSesion(
                usuario.getCodUsuario(),
                idSesion.toString(),
                usuario.getCodInstitucion(),
                1L, // codAgencia por defecto o mapeado
                fecInicio,
                fecExpira
        );

        registrarLog(loginInput, usuario.getCodUsuario(), idSesion, "LOGIN", "OK", ip, deviceId, userAgent, "N");
        return ApiResponse.success(tokenSesion, "OK");
    }

    private void registrarLog(String login, Long codUsuario, UUID idSesion, String evento, String resultado, String ip, String deviceId, String userAgent, String stsDispositivoNuevo) {
        SegLogAcceso log = new SegLogAcceso();
        log.setFecEvento(LocalDateTime.now());
        log.setNomLoginIntento(login != null ? login : "DESCONOCIDO");
        log.setCodUsuario(codUsuario);
        log.setIdSesion(idSesion);
        log.setCodEvento(evento);
        log.setCodResultado(resultado);
        log.setTxtIp(ip != null ? ip : "0.0.0.0");
        log.setTxtDeviceId(deviceId);
        log.setTxtUserAgent(userAgent);
        log.setStsDispositivoNuevo(stsDispositivoNuevo != null ? stsDispositivoNuevo : "N");

        logAccesoDao.save(log);
    }

    public ApiResponse<PoliticaPasswordResponse> obtenerPoliticaContrasena(Integer codInstitucion) {
        Optional<SegPolitica> politicaOpt = segPoliticaDao.findByCodInstitucion(codInstitucion);

        if (politicaOpt.isEmpty()) {
            return ApiResponse.error("No se encontró política configurada para la institución especificada");
        }

        SegPolitica p = politicaOpt.get();

        PoliticaPasswordResponse politicaDto = new PoliticaPasswordResponse(
                p.getNumClaveLongMin(),
                64, // El máximo permitido por BCrypt (72 bytes)[cite: 6]
                "S".equalsIgnoreCase(p.getStsReqMayuscula()),
                "S".equalsIgnoreCase(p.getStsReqMinuscula()),
                "S".equalsIgnoreCase(p.getStsReqNumero()),
                "S".equalsIgnoreCase(p.getStsReqSimbolo()),
                p.getNumClaveHistorial(),
                p.getNumClaveDiasVigencia()
        );

        return ApiResponse.success(politicaDto, "OK");
    }

    @Transactional
    public ApiResponse<Object> validarOtp(String tokenDesafio, String codigoIngresado, String deviceId, String ip, String userAgent) {
        // 1. Validar que el token sea de tipo DESAFIO[cite: 2]
        if (!jwtUtil.isTokenValid(tokenDesafio, "DESAFIO")) {
            return ApiResponse.error("TOKEN_INVALIDO");
        }

        Long codUsuario = Long.valueOf(jwtUtil.extractUserId(tokenDesafio));
        Long numOtp = jwtUtil.extractNumOtp(tokenDesafio);

        Optional<SegOtp> otpOpt = segOtpDao.findByNumOtpAndCodUsuario(numOtp, codUsuario);
        if (otpOpt.isEmpty() || !"P".equals(otpOpt.get().getStsOtp())) {
            registrarLog(null, codUsuario, null, "OTP", "OTP_INVALIDO", ip, deviceId, userAgent, "N");
            return ApiResponse.error("OTP_INVALIDO");
        }

        SegOtp otp = otpOpt.get();
        SegPolitica politica = segPoliticaDao.findByCodInstitucion(1).orElseGet(() -> null);

        // 2. Verificar expiración[cite: 9]
        if (otp.getFecExpira().isBefore(LocalDateTime.now())) {
            otp.setStsOtp("X");
            segOtpDao.save(otp);
            registrarLog(null, codUsuario, null, "OTP", "OTP_EXPIRADO", ip, deviceId, userAgent, "N");
            return ApiResponse.error("OTP_EXPIRADO");
        }

        // 3. Verificar si el código coincide con el hash BCrypt[cite: 8, 9]
        if (!passwordEncoder.matches(codigoIngresado, otp.getTxtHashCodigo())) {
            int intentos = otp.getNumIntentos() + 1;
            otp.setNumIntentos(intentos);
            int maxIntentos = politica.getNumOtpIntentosMax() != null ? politica.getNumOtpIntentosMax() : 3;

            if (intentos >= maxIntentos) {
                otp.setStsOtp("B"); // Bloqueado por intentos[cite: 5, 9]
                segOtpDao.save(otp);
                registrarLog(null, codUsuario, null, "OTP", "OTP_BLOQUEADO", ip, deviceId, userAgent, "N");
                return ApiResponse.error("OTP_BLOQUEADO");
            }

            segOtpDao.save(otp);
            registrarLog(null, codUsuario, null, "OTP", "OTP_INVALIDO", ip, deviceId, userAgent, "N");
            return ApiResponse.error("OTP_INVALIDO");
        }

        // 4. Código correcto: Marcar como validado[cite: 9]
        otp.setStsOtp("V");
        otp.setFecValidado(LocalDateTime.now());
        otp.setNumIntentos(otp.getNumIntentos() + 1);

        // --- PASO 4.1: Cerrar la sesión anterior activa del usuario ---[cite: 9, 10]
        segSesionDao.cerrarSesionesActivasDelUsuario(codUsuario);

        // 5. Crear la Sesión Definitiva en la Base de Datos (Paso 4)[cite: 10]
        UUID idSesion = UUID.randomUUID();
        LocalDateTime ahora = LocalDateTime.now();
        int minutosSesion = politica.getNumMinSesionMax() != null ? politica.getNumMinSesionMax() : 480;
        LocalDateTime fecExpiraSesion = ahora.plusMinutes(minutosSesion);

        SegSesion nuevaSesion = new SegSesion();
        nuevaSesion.setIdSesion(idSesion);
        nuevaSesion.setCodUsuario(codUsuario);
        nuevaSesion.setCodInstitucion(politica.getCodInstitucion());
        nuevaSesion.setTxtIp(ip != null ? ip : "0.0.0.0");
        nuevaSesion.setTxtDeviceId(deviceId != null ? deviceId : "UNKNOWN_DEVICE");
        nuevaSesion.setTxtUserAgent(userAgent);
        nuevaSesion.setStsMfaValidado("S"); // Pasó por MFA
        nuevaSesion.setStsSesion("A");    // Activa
        nuevaSesion.setFecInicio(ahora);
        nuevaSesion.setFecExpira(fecExpiraSesion);
        nuevaSesion.setFecUltimaActividad(ahora);

        segSesionDao.save(nuevaSesion); // <--- Esto satisface la llave foránea fk_seg_otp_ses

        // 6. Enlazar el id_sesion al OTP y guardar[cite: 8]
        otp.setIdSesion(idSesion);
        segOtpDao.save(otp);

        // 7. Generar el Token de Sesión JWT final[cite: 2, 10]
        Map<String, Object> tokenSesion = jwtUtil.generarTokenSesion(
                codUsuario,
                idSesion.toString(),
                politica.getCodInstitucion(),
                1L,
                java.sql.Timestamp.valueOf(ahora),
                java.sql.Timestamp.valueOf(fecExpiraSesion)
        );

        registrarLog(null, codUsuario, idSesion, "OTP", "OK", ip, deviceId, userAgent, "N");
        return ApiResponse.success(tokenSesion, "OK");
    }
}
