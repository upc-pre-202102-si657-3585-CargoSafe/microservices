package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record UpdateOnGoingTripCommand(
        Long id,
        Float latitude,
        Float longitude,
        Integer speed,
        Integer distance
) {
}
