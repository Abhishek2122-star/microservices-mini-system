package com.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        String secretKey = "mysupersecurejwtsecretkey1234567890abcd9876543210zyxw"; // same as user-service
        return NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
        ).build();
    }
}