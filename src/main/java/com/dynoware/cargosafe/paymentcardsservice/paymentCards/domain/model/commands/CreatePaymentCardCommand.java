package com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands;

import java.util.Date;

public record CreatePaymentCardCommand(String cardNumber, Date expiryDate, Long securityCode) {
    public CreatePaymentCardCommand {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("The card number cannot be empty");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("The expiry date number cannot be empty");
        }
        if (securityCode == null) {
            throw new IllegalArgumentException("The security code number cannot be empty");
        }
    }
}