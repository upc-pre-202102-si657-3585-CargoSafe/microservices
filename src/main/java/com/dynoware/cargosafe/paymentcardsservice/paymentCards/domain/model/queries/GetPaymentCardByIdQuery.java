package com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.queries;

public record GetPaymentCardByIdQuery(Long id) {
    public GetPaymentCardByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("The id cannot be null");
        }
    }
}
