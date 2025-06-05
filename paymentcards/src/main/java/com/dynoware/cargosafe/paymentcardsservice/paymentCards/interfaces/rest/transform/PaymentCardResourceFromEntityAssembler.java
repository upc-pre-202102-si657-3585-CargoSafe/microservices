package com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.transform;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates.PaymentCard;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.resources.PaymentCardResource;

public class PaymentCardResourceFromEntityAssembler {
    public static PaymentCardResource toResourceFromEntity(PaymentCard entity) {
        return new PaymentCardResource(entity.getId(), entity.getCardNumber(), entity.getExpiryDate(), entity.getSecurityCode());
    }
}