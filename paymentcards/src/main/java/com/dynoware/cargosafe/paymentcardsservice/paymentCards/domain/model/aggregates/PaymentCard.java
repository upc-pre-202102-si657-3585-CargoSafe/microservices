package com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands.CreatePaymentCardCommand;
import com.dynoware.cargosafe.paymentcardsservice.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.util.Date;

@Getter
@Entity
public class PaymentCard extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String cardNumber;
    @Setter
    private Date expiryDate;
    @Setter
    private Long securityCode;

    public PaymentCard() {
        this.cardNumber = Strings.EMPTY;
        this.expiryDate = new Date();
        this.securityCode = 0L;
    }
    public PaymentCard(CreatePaymentCardCommand command) {
        super();
        this.cardNumber = command.cardNumber();
        this.expiryDate = command.expiryDate();
        this.securityCode = command.securityCode();
    }
}
