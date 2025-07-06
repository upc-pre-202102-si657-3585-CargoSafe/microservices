package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record CreateVehicleCommand(
         String model,
         String plate,
         Float maxLoad,
         Float volume,
        String photoUrl
) {
}
