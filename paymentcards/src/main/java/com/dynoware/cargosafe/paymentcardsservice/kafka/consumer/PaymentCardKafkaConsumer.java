package com.dynoware.cargosafe.paymentcardsservice.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Optional;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class PaymentCardKafkaConsumer {

    private final AtomicReference<Long> latestCardId = new AtomicReference<>();

    @KafkaListener(topics = "${paymentcards.kafka.topic.card-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCardCreated(ConsumerRecord<String, Long> record) {
        Long cardId = record.value();
        latestCardId.set(cardId); // Almacena el último ID recibido
        System.out.println("Tarjeta creada con ID: " + cardId);
    }

    @KafkaListener(topics = "${paymentcards.kafka.topic.card-deleted}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCardDeleted(ConsumerRecord<String, Long> record) {
        Long cardId = record.value();
        latestCardId.set(cardId); // Almacena el último ID recibido
        System.out.println("Tarjeta eliminada con ID: " + cardId);
    }

    public Optional<Long> getLatestCardId() {
        return Optional.ofNullable(latestCardId.get());
    }
}