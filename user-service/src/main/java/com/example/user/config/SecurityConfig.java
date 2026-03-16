package com.example.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ Open endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/register").permitAll()

                        // ✅ Admin-only user management
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // ✅ Internal endpoint for order-service validation
                        .requestMatchers("/users/internal/**").hasAnyRole("USER","ADMIN")

                        // ✅ Orders accessible to both USER and ADMIN
                        .requestMatchers("/orders/**").hasAnyRole("USER","ADMIN")

                        // ✅ Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}