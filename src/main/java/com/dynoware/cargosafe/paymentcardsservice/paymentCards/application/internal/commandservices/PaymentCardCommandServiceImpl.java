package com.dynoware.cargosafe.paymentcardsservice.paymentCards.application.internal.commandservices;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates.PaymentCard;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands.CreatePaymentCardCommand;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands.DeletePaymentCardCommand;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services.PaymentCardCommandService;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.infrastructure.persistence.jpa.repositories.PaymentCardRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentCardCommandServiceImpl implements PaymentCardCommandService {
    private final PaymentCardRepository repository;

    public PaymentCardCommandServiceImpl(PaymentCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CreatePaymentCardCommand command) {
        var paymentCard = new PaymentCard(command);
        try {
            repository.save(paymentCard);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving the card: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public void handle(DeletePaymentCardCommand command) {
        if(!repository.existsById(command.id()))
            throw new IllegalArgumentException("Payment card with id %s not found".formatted(command.id()));
        try {
            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting payment card: %s".formatted(e.getMessage()));
        }
    }
}