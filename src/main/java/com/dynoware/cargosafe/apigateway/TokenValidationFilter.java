package com.dynoware.cargosafe.apigateway;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@Component
public class TokenValidationFilter implements GlobalFilter, Ordered {

    private final WebClient.Builder webClientBuilder;

    public TokenValidationFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        return webClientBuilder.build()
                .get()
                .uri("http://iam-service/internal/token/inspect")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, _ ->
                        Mono.error(new RuntimeException("Token inválido o no autorizado")))
                .onStatus(HttpStatusCode::is5xxServerError, _ ->
                        Mono.error(new RuntimeException("Error interno en el IAM")))
                .bodyToMono(String.class)
                .flatMap(user -> {
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-Authenticated-User", user)
                            .headers(http -> http.remove(HttpHeaders.AUTHORIZATION))
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
