package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Expense;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.ExpenseResource;

public class ExpenseResourceFromEntityAssembler {
    public static ExpenseResource toResourceFromEntity(Expense entity) {
        return new ExpenseResource(entity.getId(), entity.getFuelAmount(), entity.getFuelDescription(), entity.getViaticsAmount(), entity.getViaticsDescription(), entity.getTollsAmount(), entity.getTollsDescription());
    }
}