package com.trust.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    private final long expirationTime = 3600000; // 1 hora

    private Key signingKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(signingKey).build();

        System.out.println("🔐 Chave secreta (forçada): " + secretKey.substring(0, 10) + "...");
        System.out.println("🔐 Tamanho da chave: " + secretKey.getBytes(StandardCharsets.UTF_8).length + " bytes");
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        try {
            jwtParser.parseClaimsJws(token); // ← atualizado
            System.out.println("✅ Token válido.");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Token inválido: " + e.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(
                jwtParser.parseClaimsJws(token) // ← atualizado
                        .getBody()
                        .getSubject()
        );
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
