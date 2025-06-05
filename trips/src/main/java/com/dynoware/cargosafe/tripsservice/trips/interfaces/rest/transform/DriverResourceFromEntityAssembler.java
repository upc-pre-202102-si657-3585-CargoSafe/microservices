package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;


import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.DriverResource;

public class DriverResourceFromEntityAssembler {
    public static DriverResource toResourceFromEntity(Driver entity){
        return new DriverResource(entity.getId(), entity.getName(), entity.getDni(), entity.getLicense(), entity.getContactNum());
    }
}
