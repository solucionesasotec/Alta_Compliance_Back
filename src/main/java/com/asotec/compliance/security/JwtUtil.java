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
 * Utilidad JWT adaptada a la especificación de autenticación y seguridad.
 */
@Component
public class JwtUtil {

    private static final String SECRET_STRING = "mi_clave_secreta_super_segura_y_larga_para_sis_agendamiento_123";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // ==========================================
    // 1. GENERACIÓN DE TOKENS SEGÚN TIPO
    // ==========================================
    /**
     * Token de Sesión (Emitido al finalizar todo el flujo de login).
     */
    public Map<String, Object> generarTokenSesion(Long codUsuario, String idSesion, Integer codInstitucion, Long codAgencia, Date fecInicio, Date fecExpira) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(codUsuario))
                .setId(idSesion) // Mapea al claim 'jti' (id_sesion)[cite: 10]
                .claim("inst", codInstitucion)
                .claim("ag", codAgencia)
                .claim("tipo", "SESION")
                .setIssuedAt(fecInicio)
                .setExpiration(fecExpira)
                .signWith(KEY)
                .compact();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("expiresAt", fecExpira);
        return response;
    }

    /**
     * Token de Desafío (Emitido cuando la contraseña es correcta pero falta el
     * código SMS/OTP).
     */
    public Map<String, Object> generarTokenDesafio(Long codUsuario, Long numOtp, long vigenciaSegundos) {
        long currentTimeMillis = System.currentTimeMillis();
        Date fechaExpiracion = new Date(currentTimeMillis + (vigenciaSegundos * 1000));

        String token = Jwts.builder()
                .setSubject(String.valueOf(codUsuario))
                .claim("otp", numOtp)
                .claim("tipo", "DESAFIO")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(fechaExpiracion)
                .signWith(KEY)
                .compact();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("expiresAt", fechaExpiracion);
        return response;
    }

    /**
     * Token de Cambio (Emitido por contraseña vencida o primer ingreso, válido
     * por 10 minutos).
     */
    public Map<String, Object> generarTokenCambio(Long codUsuario) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTime = 10 * 60 * 1000; // 10 minutos exactos[cite: 2, 7]
        Date fechaExpiracion = new Date(currentTimeMillis + expirationTime);

        String token = Jwts.builder()
                .setSubject(String.valueOf(codUsuario))
                .claim("tipo", "CAMBIO")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(fechaExpiracion)
                .signWith(KEY)
                .compact();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("expiresAt", fechaExpiracion);
        return response;
    }

    // ==========================================
    // 2. MÉTODOS DE EXTRACCIÓN DE CLAIMS
    // ==========================================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTipo(String token) {
        return extractAllClaims(token).get("tipo", String.class);
    }

    public String extractJti(String token) {
        return extractAllClaims(token).getId();
    }

    public Integer extractCodInstitucion(String token) {
        return extractAllClaims(token).get("inst", Integer.class);
    }

    public Long extractNumOtp(String token) {
        return extractAllClaims(token).get("otp", Long.class);
    }

    // ==========================================
    // 3. VALIDACIÓN DE TOKEN Y PROPÓSITO
    // ==========================================
    /**
     * Valida la firma, expiración y el propósito estricto (tipo) del token.
     */
    public boolean isTokenValid(String token, String tipoEsperado) {
        try {
            Claims claims = extractAllClaims(token);
            String tipoToken = claims.get("tipo", String.class);

            boolean notExpired = !claims.getExpiration().before(new Date());
            boolean tipoCorrecto = tipoEsperado != null && tipoEsperado.equals(tipoToken);

            return notExpired && tipoCorrecto;
        } catch (Exception e) {
            return false;
        }
    }
}
