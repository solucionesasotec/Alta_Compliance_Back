package com.asotec.compliance.service;

import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class NotificacionService {

    private static final Logger LOGGER = Logger.getLogger(NotificacionService.class.getName());

    public void enviarCodigoOtp(String destinoMask, String codigoPlano) {
        // En desarrollo: Imprime en consola de forma segura
        // En producción: Aquí se integrará el proveedor de SMS o Correo corporativo
        LOGGER.info("==================================================");
        LOGGER.info("[MODO DESARROLLO] SMS OTP ENVIADO a " + destinoMask);
        LOGGER.info("CODIGO OTP DE ACCESO: " + codigoPlano);
        LOGGER.info("==================================================");
    }
}
