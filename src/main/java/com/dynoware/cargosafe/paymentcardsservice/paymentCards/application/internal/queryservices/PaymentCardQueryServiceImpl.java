package com.dynoware.cargosafe.paymentcardsservice.paymentCards.application.internal.queryservices;

import com.dynoware.cargosafe.paymentcardsservice.kafka.consumer.PaymentCardKafkaConsumer;
import com.dynoware.cargosafe.paymentcardsservice.kafka.producer.PaymentCardKafkaProducer;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates.PaymentCard;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.queries.GetPaymentCardByIdQuery;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services.PaymentCardQueryService;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.infrastructure.persistence.jpa.repositories.PaymentCardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCardQueryServiceImpl implements PaymentCardQueryService {
    private final PaymentCardRepository repository;
    private final PaymentCardKafkaProducer kafkaProducer;
    private final PaymentCardKafkaConsumer kafkaConsumer;

    public PaymentCardQueryServiceImpl(PaymentCardRepository repository,
                                       PaymentCardKafkaProducer kafkaProducer,
                                       PaymentCardKafkaConsumer kafkaConsumer) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public Optional<PaymentCard> handle(GetPaymentCardByIdQuery query) {

        kafkaProducer.sendCardCreatedEvent(query.id());


        Optional<Long> optionalCardId = kafkaConsumer.getLatestCardId();
        if (optionalCardId.isEmpty()) {
            throw new IllegalStateException("No se pudo obtener información de la tarjeta desde Kafka");
        }

        Long cardId = optionalCardId.get();


        return repository.findById(cardId);
    }
}