package com.dynoware.cargosafe.tripsservice.trips.application.internal.queryservices;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Expense;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllExpenseQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetExpenseByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.ExpenseQueryService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseQueryServiceImpl implements ExpenseQueryService {

    private final ExpenseRepository expenseRepository;

    public ExpenseQueryServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Optional<Expense> handle(GetExpenseByIdQuery query) {
        return expenseRepository.findById(query.id());
    }

    @Override
    public List<Expense> handle(GetAllExpenseQuery query) {
        return expenseRepository.findAll();
    }
}

