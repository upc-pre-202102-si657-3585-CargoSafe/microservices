package com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.resources;

import java.util.Date;

public record PaymentCardResource(Long id, String cardNumber, Date expiryDate, Long securityCode) {
}
