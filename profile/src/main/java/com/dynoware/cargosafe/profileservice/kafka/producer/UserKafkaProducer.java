package com.dynoware.cargosafe.profileservice.kafka.producer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;               // Para enviar Long (userId)
    private final KafkaTemplate<String, String> stringKafkaTemplate;        // Para enviar String (username)

    public UserKafkaProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Qualifier("stringKafkaTemplate") KafkaTemplate<String, String> stringKafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.stringKafkaTemplate = stringKafkaTemplate;
    }

    public void sendUserIdRequest(String username) {
        stringKafkaTemplate.send("iam.get-user-id-by-username", username); // usa StringSerializer
        System.out.println("ℹ Enviando solicitud a IAM para el username: " + username);

    }

    public void sendUsernameRequest(Long userId) {
        kafkaTemplate.send("iam.get-username-by-user-id", userId); // usa LongSerializer
    }
}
