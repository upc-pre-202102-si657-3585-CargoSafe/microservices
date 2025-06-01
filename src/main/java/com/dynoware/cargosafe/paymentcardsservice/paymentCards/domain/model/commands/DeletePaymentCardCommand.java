package com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands;

public record DeletePaymentCardCommand(Long id) {
    public DeletePaymentCardCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Payment card ID is required");
        }
    }
}