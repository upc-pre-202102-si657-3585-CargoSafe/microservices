package com.dynoware.cargosafe.trips.interfaces.rest.resources;

public record CreateOnGoingTripResource(
        Float latitude,
        Float longitude,
        Integer speed,
        Integer distance
) {
}
