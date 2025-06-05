package com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services;



import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates.PaymentCard;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.queries.GetPaymentCardByIdQuery;

import java.util.Optional;

public interface PaymentCardQueryService {
    Optional<PaymentCard> handle(GetPaymentCardByIdQuery query);
}
