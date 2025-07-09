package com.dynoware.cargosafe.requestservice.request.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaUserValidationConsumer {
    private final Map<String, CompletableFuture<Boolean>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<Boolean> registerRequest(String correlationId) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        return future;
    }

    @KafkaListener(topics = "iam.user.validation.response", groupId = "request-service-group")
    public void listen(ConsumerRecord<String, Map<String, Object>> record) {
        Map<String, Object> message = record.value();
        String correlationId = (String) message.get("correlationId");
        Object existsObj = message.get("exists");
        boolean exists = false;
        if (existsObj instanceof Boolean b) exists = b;
        else if (existsObj instanceof String s) exists = Boolean.parseBoolean(s);
        else if (existsObj instanceof Number n) exists = n.intValue() == 1;
        CompletableFuture<Boolean> future = pendingRequests.remove(correlationId);
        if (future != null) future.complete(exists);
    }
} 