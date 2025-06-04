package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.commands.UpdateOnGoingTripCommand;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.UpdateOnGoingTripResource;

public class UpdateOnGoingTripCommandFromResourceAssembler {
    public static UpdateOnGoingTripCommand toCommandFromResource(Long onGoingTripId, UpdateOnGoingTripResource resource) {
        return new UpdateOnGoingTripCommand(onGoingTripId, resource.latitude(), resource.longitude(), resource.speed(), resource.distance());
    }
}
