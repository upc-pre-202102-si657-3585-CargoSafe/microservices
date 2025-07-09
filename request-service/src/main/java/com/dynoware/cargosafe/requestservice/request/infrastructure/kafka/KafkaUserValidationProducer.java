package com.dynoware.cargosafe.requestservice.request.infrastructure.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaUserValidationProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.user.validation.request:iam.user.validation.request}")
    private String validationRequestTopic;

    public KafkaUserValidationProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserValidationRequest(Long userId, String correlationId) {
        Map<String, Object> message = new HashMap<>();
        message.put("userId", userId);
        message.put("correlationId", correlationId);
        kafkaTemplate.send(validationRequestTopic, correlationId, message);
    }
} 