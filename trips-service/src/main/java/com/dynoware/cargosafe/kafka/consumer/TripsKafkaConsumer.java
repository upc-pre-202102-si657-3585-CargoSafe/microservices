package com.dynoware.cargosafe.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TripsKafkaConsumer {

    @KafkaListener(topics = "iam.user-deleted", groupId = "trips-service-group")
    public void handleUserDeleted(ConsumerRecord<String, Map<String, Object>> record) {
        Map<String, Object> event = record.value();
        Long userId = (Long) event.get("userId");

        System.out.println("Usuario eliminado: " + userId + ". Procesando trips huérfanos...");
        // Lógica para manejar trips de usuario eliminado
    }
}