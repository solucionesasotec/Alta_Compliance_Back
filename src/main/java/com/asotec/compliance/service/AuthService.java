package com.asotec.compliance.service;

import com.asotec.compliance.dao.SegLogAccesoDao;
import com.asotec.compliance.dao.SegPoliticaDao;
import com.asotec.compliance.dao.SegUsuarioDao;
import com.asotec.compliance.dto.PoliticaPasswordResponse;
import com.asotec.compliance.entity.SegUsuario;
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
            // Generación de Token de Desafío (el código OTP real se acoplará en el Paso 3)[cite: 2, 8]
            long vigenciaOtp = politica.getNumOtpSegVigencia() != null ? politica.getNumOtpSegVigencia() : 300L;
            Map<String, Object> tokenDesafio = jwtUtil.generarTokenDesafio(usuario.getCodUsuario(), 0L, vigenciaOtp);
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
}
