package com.asotec.compliance.controller;

import com.asotec.compliance.dto.PoliticaPasswordResponse;
import com.asotec.compliance.entity.ApiResponse;
import com.asotec.compliance.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login") // Endpoint especificado en[cite: 1]
    public ResponseEntity<ApiResponse<Object>> login(
            @RequestParam String user,
            @RequestParam String password,
            @RequestHeader(value = "X-Device-Id", required = false) String deviceId,
            HttpServletRequest httpRequest
    ) {
        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");

        ApiResponse<Object> response = authService.procesarLogin(user, password, deviceId, ip, userAgent);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/otp/validar")
    public ResponseEntity<ApiResponse<Object>> validarOtp(
            @RequestHeader("Authorization") String authHeader,
            @RequestHeader(value = "X-Device-Id", required = false) String deviceId,
            @RequestParam String codigo,
            HttpServletRequest httpRequest
    ) {
        String tokenDesafio = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");

        ApiResponse<Object> response = authService.validarOtp(tokenDesafio, codigo, null, ip, userAgent);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/password/policy") // Endpoint exacto de la especificación[cite: 1]
    public ResponseEntity<ApiResponse<PoliticaPasswordResponse>> obtenerPolitica(
            @RequestParam(value = "codInstitucion", defaultValue = "1") Integer codInstitucion) {

        ApiResponse<PoliticaPasswordResponse> response = authService.obtenerPoliticaContrasena(codInstitucion);
        return ResponseEntity.ok(response);
    }
}
