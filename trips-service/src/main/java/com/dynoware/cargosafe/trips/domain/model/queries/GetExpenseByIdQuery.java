package com.dynoware.cargosafe.trips.domain.model.queries;

public record GetExpenseByIdQuery(Long id) {
    public GetExpenseByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}