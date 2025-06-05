package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateEvidenceCommand;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateEvidenceResource;

public class CreateEvidenceCommandFromResourceAssembler {
    public static CreateEvidenceCommand toCommandFromResource(CreateEvidenceResource resource){
        return new CreateEvidenceCommand(
                resource.link(),
                resource.trip_id()
        );
    }
}
