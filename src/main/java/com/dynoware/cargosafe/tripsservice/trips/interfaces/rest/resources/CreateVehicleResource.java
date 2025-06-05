package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources;

public record CreateVehicleResource(
        String model,
        String plate,
        float maxLoad,
        float volume,
        String photoUrl
) {
}
