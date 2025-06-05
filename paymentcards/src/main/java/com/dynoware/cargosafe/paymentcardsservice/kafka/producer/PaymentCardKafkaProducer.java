package com.dynoware.cargosafe.paymentcardsservice.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentCardKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCardCreatedEvent(Long cardId) {
        kafkaTemplate.send("paymentcards.card-created", cardId);
    }

    public void sendCardDeletedEvent(Long cardId) {
        kafkaTemplate.send("paymentcards.card-deleted", cardId);
    }
}