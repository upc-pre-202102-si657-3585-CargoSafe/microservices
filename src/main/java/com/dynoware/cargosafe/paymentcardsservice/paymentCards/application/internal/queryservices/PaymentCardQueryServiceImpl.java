package com.dynoware.cargosafe.paymentcardsservice.paymentCards.application.internal.queryservices;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates.PaymentCard;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.queries.GetPaymentCardByIdQuery;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services.PaymentCardQueryService;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.infrastructure.persistence.jpa.repositories.PaymentCardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCardQueryServiceImpl implements PaymentCardQueryService {
    private final PaymentCardRepository repository;

    public PaymentCardQueryServiceImpl(PaymentCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PaymentCard> handle(GetPaymentCardByIdQuery query) {
        return repository.findById(query.id());
    }
}
