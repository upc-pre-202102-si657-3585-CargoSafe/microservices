package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record CreateVehicleCommand(
         String model,
         String plate,
         float maxLoad,
         float volume,
        String photoUrl
) {
}
