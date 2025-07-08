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
                                // Actuator endpoints (para monitoreo y health checks)
                                "/actuator/**",
                                "/actuator/health",
                                "/actuator/info",
                                "/actuator/metrics/**",

                                // Autenticación
                                "/api/v1/authentication/**",

                                // Swagger y documentación
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",

                                // IAM Service
                                "/iam/v3/api-docs",
                                "/iam/**",

                                // Profile Service
                                "/profile/**",
                                "/profile/v3/api-docs",
                                "/api/v1/profiles/**",

                                // Companies Service
                                "/companies/v3/api-docs",
                                "/api/v1/companie/**",

                                // PaymentCards Service
                                "/paymentcards/v3/api-docs",
                                "/api/v1/paymentcards/**",

                                // Request Service
                                "/request/v3/api-docs",
                                "/api/v1/requestServices/**",

                                // Trips Service
                                "/trips/v3/api-docs",
                                "/api/v1/trips/**",
                                "/api/v1/drivers/**",
                                "/api/v1/vehicles/**",
                                "/api/v1/alert/**",
                                "/api/v1/expense/**",
                                "/api/v1/evidence/**",
                                "/api/v1/on-going-trip/**").permitAll()
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
        config.addAllowedOriginPattern("*"); // Usar pattern en lugar de origin específico
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setExposedHeaders(List.of("Authorization")); // Opcional
        config.setAllowCredentials(false); // Cambiar a false para evitar el conflicto

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


