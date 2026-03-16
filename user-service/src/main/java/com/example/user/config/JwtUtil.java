package com.example.user.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysupersecurejwtsecretkey1234567890abcd9876543210zyxw";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String generateToken(String email, String role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600_000))
                .sign(algorithm);
    }

    public boolean isTokenValid(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    public String extractRole(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("role").asString();
    }

    public String extractUsername(String token) {
        return extractEmail(token);
    }

    // ✅ Added for JwtFilter compatibility
    public DecodedJWT extractClaims(String token) {
        return verifier.verify(token);
    }
}