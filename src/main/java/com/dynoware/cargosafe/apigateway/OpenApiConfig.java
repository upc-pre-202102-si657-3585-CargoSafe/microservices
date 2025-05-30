package com.dynoware.cargosafe.apigateway;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            System.out.println("Authorization header received: " + token); // TEMPORAL

            // Puedes reenviar el token si es necesario
            if (token != null) {
                ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            }

            return chain.filter(exchange);
        };
    }
}
