package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource){
        return new CreateVehicleCommand(
                resource.model(),
                resource.plate(),
                resource.maxLoad(),
                resource.volume(),
                resource.photoUrl()
        );
    }
}
