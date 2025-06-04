package com.dynoware.cargosafe.trips.interfaces.rest.transform;


import com.dynoware.cargosafe.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.DriverResource;

public class DriverResourceFromEntityAssembler {
    public static DriverResource toResourceFromEntity(Driver entity){
        return new DriverResource(entity.getId(), entity.getName(), entity.getDni(), entity.getLicense(), entity.getContactNum());
    }
}
