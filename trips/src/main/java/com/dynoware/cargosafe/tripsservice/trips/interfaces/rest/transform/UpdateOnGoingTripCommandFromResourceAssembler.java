package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateOnGoingTripCommand;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.UpdateOnGoingTripResource;

public class UpdateOnGoingTripCommandFromResourceAssembler {
    public static UpdateOnGoingTripCommand toCommandFromResource(Long onGoingTripId, UpdateOnGoingTripResource resource) {
        return new UpdateOnGoingTripCommand(onGoingTripId, resource.latitude(), resource.longitude(), resource.speed(), resource.distance());
    }
}
