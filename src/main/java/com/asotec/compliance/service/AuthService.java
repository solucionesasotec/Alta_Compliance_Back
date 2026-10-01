package com.asotec.compliance.service;

import com.asotec.compliance.dao.SegPoliticaDao;
import com.asotec.compliance.dao.SegUsuarioDao;
import com.asotec.compliance.dto.PoliticaPasswordResponse;
import com.asotec.compliance.entity.SegUsuario;
import com.asotec.compliance.entity.SegPolitica;
import com.asotec.compliance.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    private SegUsuarioDao usuarioDao;

    @Autowired
    private SegPoliticaDao segPoliticaDao;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ApiResponse procesarLogin(String login, String clave) {
        SegUsuario usuario = usuarioDao.findByNomLoginIgnoreCase(login);

        // 1. El usuario no existe[cite: 7]
        if (usuario == null) {
            // Mitigación de time-based attacks: verificar un hash falso
            passwordEncoder.matches(clave, "$2a$10$falsoHashDeEjemploParaTardarLoMismo12345");
//            registrarLogAcceso(login, null, "USUARIO_NO_EXISTE", "LOGIN"); // Implementar auditoría[cite: 7]
            return ApiResponse.error("USUARIO_NO_EXISTE");
        }

        // 2. Bloqueo temporal vigente[cite: 7]
        if (usuario.getFecBloqueoHasta() != null && usuario.getFecBloqueoHasta().isAfter(LocalDateTime.now())) {
//            throw new CustomAuthException("USUARIO_BLOQUEADO", 401);
            return ApiResponse.error("USUARIO_BLOQUEADO");
        }

        // 3. Contraseña incorrecta[cite: 7]
        if (!passwordEncoder.matches(clave, usuario.getTxtHashClave())) {
            // Sumar intento atómico consultando la política del usuario[cite: 7]
            usuarioDao.registrarIntentoFallido(usuario.getCodUsuario(), 5, 30); // Valores de ejemplo, deben salir de SegPolitica[cite: 6]
//            registrarLogAcceso(login, usuario.getCodUsuario(), "CLAVE_INCORRECTA", "LOGIN");
//            throw new CustomAuthException("CREDENCIALES_INVALIDAS", 401);
            return ApiResponse.error("CREDENCIALES_INVALIDAS");
        }

        // 4. Contraseña correcta: reinicio de intentos[cite: 7]
        usuarioDao.reiniciarIntentosFallidos(usuario.getCodUsuario());

        // 5. Usuario inactivo o bloqueado[cite: 7]
        if ("I".equals(usuario.getStsUsuario()) || "B".equals(usuario.getStsUsuario())) {
//            throw new CustomAuthException("USUARIO_INACTIVO", 401);
            return ApiResponse.error("USUARIO_INACTIVO");
        }

        // 6 y 7. Segundo factor (Generar código OTP y token de desafío)[cite: 7, 8]
        // Retorna OTP_REQUERIDO con el token de desafío (Válido típicamente por 300s)[cite: 2, 8]
//        return generarOtpResponse(usuario);
        return null;
    }

    public PoliticaPasswordResponse obtenerPoliticaContrasena(Integer codInstitucion) {
        Optional<SegPolitica> politicaOpt = segPoliticaDao.findByCodInstitucion(codInstitucion);

//        if (politicaOpt.isEmpty()) {
//            // Error de configuración registrado en el log técnico, aplica valores por defecto[cite: 5]
//            LOGGER.warning("Advertencia: La institución " + codInstitucion + " no tiene política configurada. Aplicando valores por defecto.");
//            return new PoliticaPasswordResponse(
//                    10, // longitudMinima por defecto[cite: 5]
//                    64, // longitudMaxima[cite: 6]
//                    true, // requiereMayuscula[cite: 5]
//                    true, // requiereMinuscula[cite: 5]
//                    true, // requiereNumero[cite: 5]
//                    true, // requiereSimbolo[cite: 5]
//                    5, // noRepetirUltimas (num_clave_historial)[cite: 6]
//                    90 // diasVigencia (num_clave_dias_vigencia)[cite: 6]
//            );
//        }

        SegPolitica p = politicaOpt.get();

        return new PoliticaPasswordResponse(
                p.getNumClaveLongMin(),
                64, // El máximo permitido por BCrypt (72 bytes)[cite: 6]
                "S".equalsIgnoreCase(p.getStsReqMayuscula()),
                "S".equalsIgnoreCase(p.getStsReqMinuscula()),
                "S".equalsIgnoreCase(p.getStsReqNumero()),
                "S".equalsIgnoreCase(p.getStsReqSimbolo()),
                p.getNumClaveHistorial(),
                p.getNumClaveDiasVigencia()
        );
    }
}
