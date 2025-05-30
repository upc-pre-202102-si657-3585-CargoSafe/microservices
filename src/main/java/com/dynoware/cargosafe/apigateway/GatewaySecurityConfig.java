package com.dynoware.cargosafe.apigateway;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Value("${authorization.jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/api/v1/authentication/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/iam/v3/api-docs",
                                "/iam/**",
                                "iam/swagger-ui.html",
                                "profile/swagger-ui.html",
                                "/profile/**",
                                "/profile/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/webjars/iam/swagger-ui.html" +
                                        "/webjars/profile/swagger-ui.html").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder()))
                );

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Cambia esto si quieres restringir a dominios específicos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setExposedHeaders(List.of("Authorization")); // Opcional
        config.setAllowCredentials(true); // Si necesitas enviar cookies o headers de autenticación

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        // 👇 Forzar el algoritmo HS384 explícitamente
        return NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS384)
                .build();
    }

    @Bean
    public GlobalFilter logAuthorizationHeader() {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            System.out.println("Authorization header received: " + authHeader);
            return chain.filter(exchange);
        };
    }

}


