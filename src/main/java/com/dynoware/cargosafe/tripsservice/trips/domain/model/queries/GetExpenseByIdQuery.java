package com.dynoware.cargosafe.tripsservice.trips.domain.model.queries;

public record GetExpenseByIdQuery(Long id) {
    public GetExpenseByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}