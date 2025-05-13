package com.dynoware.cargosafe.iamservice.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test-topic", groupId = "iam-service-group")
    public void listen(String message) {
        System.out.println("Mensaje recibido desde Kafka: " + message);
    }
}
