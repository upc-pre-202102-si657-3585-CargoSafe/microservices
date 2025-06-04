package com.dynoware.cargosafe.trips.infrastructure.persistence.jpa;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(Long id);

    List<Expense> findAll();
}
