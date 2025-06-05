package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources;

public record UpdateOnGoingTripResource(
        Float latitude,
        Float longitude,
        Integer speed,
        Integer distance
) {
}
