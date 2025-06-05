package com.dynoware.cargosafe.companieservice.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserKafkaConsumer {

    private Long latestUserId;
    private String latestUsername;

    @KafkaListener(topics = "iam.response.user-id", groupId = "profileservice-group")
    public void receiveUserId(ConsumerRecord<String, Long> record) {
        latestUserId = record.value();
        System.out.println("Recibido userId: " + latestUserId);
    }

    @KafkaListener(topics = "iam.response.username", groupId = "profileservice-group")
    public void receiveUsername(ConsumerRecord<String, String> record) {
        latestUsername = record.value();
        System.out.println("Recibido username: " + latestUsername);
    }

    public Optional<Long> getLatestUserId() {
        return Optional.ofNullable(latestUserId);
    }

    public Optional<String> getLatestUsername() {
        return Optional.ofNullable(latestUsername);
    }
}
