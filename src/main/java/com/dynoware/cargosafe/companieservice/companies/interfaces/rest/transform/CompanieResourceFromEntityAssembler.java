package com.dynoware.cargosafe.companieservice.companies.interfaces.rest.transform;

import com.dynoware.cargosafe.companieservice.companies.domain.model.aggregates.Companie;
import com.dynoware.cargosafe.companieservice.companies.interfaces.rest.resources.CompanieResource;

public class CompanieResourceFromEntityAssembler {
    public static CompanieResource toResourceFromEntity(Companie entity) {
        return new CompanieResource(entity.getId(), entity.getCompanieName());
    }
}