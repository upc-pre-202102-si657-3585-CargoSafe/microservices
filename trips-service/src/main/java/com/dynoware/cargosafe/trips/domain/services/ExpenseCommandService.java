package com.dynoware.cargosafe.trips.domain.services;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Expense;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateExpenseCommand;

import java.util.Optional;

public interface ExpenseCommandService {

    Optional<Expense> handle(CreateExpenseCommand command);
}
