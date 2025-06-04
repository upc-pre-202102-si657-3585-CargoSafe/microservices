package com.dynoware.cargosafe.trips.domain.model.commands;

public record UpdateOnGoingTripCommand(
        Long id,
        Float latitude,
        Float longitude,
        Integer speed,
        Integer distance
) {
}
