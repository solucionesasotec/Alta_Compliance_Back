/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asotec.compliance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author cz863
 */
@Component
public class JwtUtil {

    // 1. DEFINIMOS UNA CLAVE FIJA (Debe ser larga para HS256)
    // En producción esto debería venir de application.properties
    private static final String SECRET_STRING = "mi_clave_secreta_super_segura_y_larga_para_sis_agendamiento_123";
    // 2. CONVERTIMOS ESA STRING EN UN OBJETO KEY
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
    private static final long EXPIRATION_TIME = 1800000; // 24 horas

    public Map<String, Object> generarToken(String userId, Long codEmpleado, String rolEmpleado) {
        long currentTimeMillis = System.currentTimeMillis();
        Date fechaExpiracion = new Date(currentTimeMillis + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(userId)
                .claim("codEmpleado", codEmpleado)
                .claim("rol", rolEmpleado)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(fechaExpiracion)
                .signWith(KEY)
                .compact();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("expiresAt", fechaExpiracion); // Enviamos el objeto Date o puedes formatearlo a String

        return response;
    }

    // 1. Método base para leer todas las propiedades (Claims) del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY) // La misma KEY estática que usaste para firmar
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 2. Extraer el USER ID (viene en el campo "sub")
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            // Al intentar extraer los claims, JJWT valida la firma y la fecha de expiración
            Claims claims = extractAllClaims(token);

            // Verificación manual adicional de la fecha (aunque parseClaimsJws ya lo hace)
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            // Si el token expiró, la firma no coincide o el formato es erróneo
            return false;
        }
    }

    public Long extractCodEmpleado(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("codEmpleado", Long.class);
    }

    public String extractRolEmpleado(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("rol", String.class);
    }
}
