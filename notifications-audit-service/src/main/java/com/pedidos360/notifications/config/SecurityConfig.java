package com.pedidos360.notifications.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Apagamos la protección CSRF. Crítico para permitir peticiones POST desde Bruno/Postman.
            .csrf(csrf -> csrf.disable())
            
            // 2. Le decimos que autorice absolutamente todas las peticiones entrantes sin pedir login.
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
            
        return http.build();
    }
}