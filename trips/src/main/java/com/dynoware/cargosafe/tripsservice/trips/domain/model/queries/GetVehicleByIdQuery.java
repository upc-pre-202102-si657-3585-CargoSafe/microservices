package com.dynoware.cargosafe.tripsservice.trips.domain.model.queries;

public record GetVehicleByIdQuery(Long id) {
    public GetVehicleByIdQuery {
        if (id == null){
            throw new IllegalArgumentException("id cannot be null");
        }
        if (id < 0){
            throw new IllegalArgumentException("id cannot be negative");
        }
    }
}
