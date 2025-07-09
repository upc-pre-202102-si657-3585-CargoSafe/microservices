package com.dynoware.cargosafe.requestservice.request.domain.services;

import com.dynoware.cargosafe.requestservice.request.domain.exceptions.RequestServiceException;
import com.dynoware.cargosafe.requestservice.request.infrastructure.kafka.KafkaUserValidationProducer;
import com.dynoware.cargosafe.requestservice.request.infrastructure.kafka.KafkaUserValidationConsumer;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UserValidationService {
    private final KafkaUserValidationProducer producer;
    private final KafkaUserValidationConsumer consumer;

    public UserValidationService(KafkaUserValidationProducer producer, KafkaUserValidationConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public boolean validateUserExists(Long userId) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Boolean> future = consumer.registerRequest(correlationId);
        producer.sendUserValidationRequest(userId, correlationId);
        try {
            Boolean exists = future.get(3, TimeUnit.SECONDS);
            if (exists == null) throw new RequestServiceException("No se pudo validar el usuario: null");
            return exists;
        } catch (Exception e) {
            throw new RequestServiceException("No se pudo validar el usuario: " + e.getMessage(), e);
        }
    }
} 