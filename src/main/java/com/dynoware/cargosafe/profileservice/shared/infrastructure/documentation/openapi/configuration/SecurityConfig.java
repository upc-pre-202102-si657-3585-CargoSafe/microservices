package com.dynoware.cargosafe.profileservice.shared.infrastructure.documentation.openapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitimos swagger y api docs sin autenticacion
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/profile/v3/api-docs",
                                "/swagger-resources/**",
                                "/profile/**",
                                "/webjars/**",
                                "profile/swagger-ui.html"
                        ).permitAll()
                        // Cualquier otra peticion requiere autenticacion
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
