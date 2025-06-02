package com.dynoware.cargosafe.paymentcardsservice.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardKafkaConsumer {

    @KafkaListener(topics = "${paymentcards.kafka.topic.card-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCardCreated(ConsumerRecord<String, Long> record) {
        Long cardId = record.value();
        System.out.println("Tarjeta creada con ID: " + cardId);
    }

    @KafkaListener(topics = "${paymentcards.kafka.topic.card-deleted}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCardDeleted(ConsumerRecord<String, Long> record) {
        Long cardId = record.value();
        System.out.println("Tarjeta eliminada con ID: " + cardId);
    }
}