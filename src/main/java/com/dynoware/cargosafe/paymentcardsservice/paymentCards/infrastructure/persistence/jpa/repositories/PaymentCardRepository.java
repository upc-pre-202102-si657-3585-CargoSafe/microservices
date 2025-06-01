package com.dynoware.cargosafe.paymentcardsservice.paymentCards.infrastructure.persistence.jpa.repositories;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.aggregates.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    Optional<PaymentCard> findById(Long id);
}
