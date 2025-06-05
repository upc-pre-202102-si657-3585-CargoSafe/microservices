package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Expense;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllExpenseQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetExpenseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ExpenseQueryService {

    Optional<Expense> handle(GetExpenseByIdQuery query);

    List<Expense> handle(GetAllExpenseQuery query);
}
