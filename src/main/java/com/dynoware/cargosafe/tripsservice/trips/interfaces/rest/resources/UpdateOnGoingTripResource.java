package com.dynoware.cargosafe.trips.interfaces.rest.resources;

public record UpdateOnGoingTripResource(
        Float latitude,
        Float longitude,
        Integer speed,
        Integer distance
) {
}
