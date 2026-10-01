package com.asotec.compliance.controller;

import com.asotec.compliance.dto.PoliticaPasswordResponse;
import com.asotec.compliance.entity.ApiResponse;
import com.asotec.compliance.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

//    @PostMapping("/login") // Endpoint especificado en[cite: 1]
//    public ResponseEntity<?> login(@RequestBody ApiResponse request) {
//        // request contiene "usuario" y "clave"
//        // Los encabezados X-Device-Id y la IP se pueden inyectar con HttpServletRequest
//        LoginResponse response = authService.procesarLogin(request.getUsuario(), request.getClave());
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/otp/validar") // Endpoint especificado en[cite: 1]
//    public ResponseEntity<?> validarOtp(@RequestBody OtpRequest request) {
//        // Lógica para validar código usando el token de desafío enviado en el Header
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("/password/policy") // Endpoint exacto de la especificación[cite: 1]
    public ResponseEntity<PoliticaPasswordResponse> obtenerPolitica(
            @RequestParam(value = "codInstitucion", defaultValue = "1") Integer codInstitucion) {

        // Nota: En producción, 'codInstitucion' se extraerá de forma segura de los claims del JWT validado en el filtro.
        PoliticaPasswordResponse response = authService.obtenerPoliticaContrasena(codInstitucion);
        return ResponseEntity.ok(response);
    }
}
