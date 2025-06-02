package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.aggregates.OnGoingTrip;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.OnGoingTripResource;

public class OnGoingTripResourceFromEntityAssembler {
    public static OnGoingTripResource toResourceFromEntity(OnGoingTrip entity) {
        return new OnGoingTripResource(
                entity.getId(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getSpeed(),
                entity.getDistance()
        );
    }
}
