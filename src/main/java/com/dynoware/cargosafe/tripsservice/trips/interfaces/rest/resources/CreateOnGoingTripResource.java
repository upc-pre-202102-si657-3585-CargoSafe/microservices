package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources;

public record CreateOnGoingTripResource(
        Float latitude,
        Float longitude,
        Integer speed,
        Integer distance
) {
}
