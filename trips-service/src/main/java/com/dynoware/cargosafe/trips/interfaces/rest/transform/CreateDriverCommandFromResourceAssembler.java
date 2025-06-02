package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.commands.CreateDriverCommand;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.CreateDriverResource;

public class CreateDriverCommandFromResourceAssembler {

    public static CreateDriverCommand toCommandFromResource(CreateDriverResource resource){
        return new CreateDriverCommand(resource.name(),resource.dni(), resource.license(), resource.contactNum(),resource.photoUrl());
    }
}
