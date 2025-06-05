package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateOnGoingTripCommand;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateOnGoingTripResource;

public class CreateOnGoingTripCommandFromResourceAssembler {
    public static CreateOnGoingTripCommand toCommandFromResource(CreateOnGoingTripResource resource) {
        return new CreateOnGoingTripCommand(
                resource.latitude(),
                resource.longitude(),
                resource.speed(),
                resource.distance()
        );
    }
}
