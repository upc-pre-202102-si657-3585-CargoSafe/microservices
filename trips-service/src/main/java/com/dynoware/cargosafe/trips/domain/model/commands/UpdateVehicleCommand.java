package com.dynoware.cargosafe.trips.domain.model.commands;

public record UpdateVehicleCommand(
        Long id,
        String model,
        String plate,
        float maxLoad,
        float volume,
        String photoUrl
) {
}
