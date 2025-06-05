package com.dynoware.cargosafe.tripsservice.trips.domain.model.queries;

public record GetVehicleByModelQuery(String model) {
    public GetVehicleByModelQuery {
        if (model.isEmpty()) {
            throw new IllegalArgumentException("Vehicle model cannot be empty");
        }
    }
}
