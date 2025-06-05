package com.dynoware.cargosafe.companieservice.companies.interfaces.rest.transform;

import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.CreateCompanieCommand;
import com.dynoware.cargosafe.companieservice.companies.interfaces.rest.resources.CreateCompanieResource;

public class CreateCompanieCommandFromResourceAssembler {
    public static CreateCompanieCommand toCommandFromResource(CreateCompanieResource resource) {
        return new CreateCompanieCommand(resource.companieName());
    }
}
