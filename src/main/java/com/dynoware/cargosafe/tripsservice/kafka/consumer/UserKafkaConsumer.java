package com.dynoware.cargosafe.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserKafkaConsumer {

    private Long latestUserId;
    private String latestUsername;

    @KafkaListener(topics = "iam.response.user-id", groupId = "trips-service-group")
    public void receiveUserId(ConsumerRecord<String, Long> record) {
        latestUserId = record.value();
        System.out.println("Trips-service recibió userId: " + latestUserId);
    }

    @KafkaListener(topics = "iam.response.username", groupId = "trips-service-group")
    public void receiveUsername(ConsumerRecord<String, String> record) {
        latestUsername = record.value();
        System.out.println("Trips-service recibió username: " + latestUsername);
    }

    public Optional<Long> getLatestUserId() {
        return Optional.ofNullable(latestUserId);
    }

    public Optional<String> getLatestUsername() {
        return Optional.ofNullable(latestUsername);
    }
}