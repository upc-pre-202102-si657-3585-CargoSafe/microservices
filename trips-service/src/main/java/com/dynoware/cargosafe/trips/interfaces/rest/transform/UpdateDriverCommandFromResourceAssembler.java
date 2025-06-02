package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.commands.UpdateDriverCommand;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.UpdateDriverResource;

public class UpdateDriverCommandFromResourceAssembler {
    public static UpdateDriverCommand toCommandFromResource(Long driverId,
                                                            UpdateDriverResource resource)
    {
        return new UpdateDriverCommand(driverId,
                resource.name(),
                resource.dni(),
                resource.license(),
                resource.contactNum(),
                resource.photoUrl());
    }
}
