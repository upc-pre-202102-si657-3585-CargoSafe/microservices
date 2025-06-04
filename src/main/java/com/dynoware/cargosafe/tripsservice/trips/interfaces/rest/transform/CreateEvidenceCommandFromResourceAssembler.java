package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.commands.CreateEvidenceCommand;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.CreateEvidenceResource;

public class CreateEvidenceCommandFromResourceAssembler {
    public static CreateEvidenceCommand toCommandFromResource(CreateEvidenceResource resource){
        return new CreateEvidenceCommand(
                resource.link(),
                resource.trip_id()
        );
    }
}
