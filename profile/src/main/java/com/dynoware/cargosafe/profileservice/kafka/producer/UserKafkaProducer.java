package com.dynoware.cargosafe.profileservice.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserIdRequest(String username) {
        kafkaTemplate.send("iam.get-user-id-by-username", username);
    }

    public void sendUsernameRequest(Long userId) {
        kafkaTemplate.send("iam.get-username-by-user-id", userId);
    }
}
