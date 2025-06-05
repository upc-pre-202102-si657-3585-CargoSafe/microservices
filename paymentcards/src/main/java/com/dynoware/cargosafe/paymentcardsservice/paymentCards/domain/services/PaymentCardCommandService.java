package com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands.CreatePaymentCardCommand;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands.DeletePaymentCardCommand;

public interface PaymentCardCommandService {
    void handle(CreatePaymentCardCommand command);
    void handle(DeletePaymentCardCommand command);
}
