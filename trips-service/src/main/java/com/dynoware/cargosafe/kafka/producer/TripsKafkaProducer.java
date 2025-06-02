package com.dynoware.cargosafe.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TripsKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TripsKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTripCreated(Long tripId, String tripName, Long userId) {
        Map<String, Object> event = Map.of(
                "tripId", tripId,
                "tripName", tripName,
                "userId", userId,
                "eventType", "TRIP_CREATED",
                "timestamp", System.currentTimeMillis()
        );
        kafkaTemplate.send("trips.trip-created", event);
    }

    public void sendTripUpdated(Long tripId, String status) {
        Map<String, Object> event = Map.of(
                "tripId", tripId,
                "status", status,
                "eventType", "TRIP_UPDATED",
                "timestamp", System.currentTimeMillis()
        );
        kafkaTemplate.send("trips.trip-updated", event);
    }

    public void sendTripDeleted(Long tripId) {
        Map<String, Object> event = Map.of(
                "tripId", tripId,
                "eventType", "TRIP_DELETED",
                "timestamp", System.currentTimeMillis()
        );
        kafkaTemplate.send("trips.trip-deleted", event);
    }
}