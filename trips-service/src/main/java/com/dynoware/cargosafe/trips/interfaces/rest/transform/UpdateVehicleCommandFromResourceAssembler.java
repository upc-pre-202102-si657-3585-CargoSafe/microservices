package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.commands.UpdateVehicleCommand;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.UpdateVehicleResource;

public class UpdateVehicleCommandFromResourceAssembler {
    public static UpdateVehicleCommand toCommandFromResource(Long vehicleId, UpdateVehicleResource resource) {
        return new UpdateVehicleCommand(
                vehicleId,
                resource.model(),
                resource.plate(),
                resource.maxLoad(),
                resource.volume(),
                resource.photoUrl()
        );
    }
}