package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource transformResourceFromEntity(Vehicle entity) {
        return new VehicleResource(
                entity.getId(),
                entity.getModel(),
                entity.getPlate(),
                entity.getVolume(),
                entity.getMaxLoad(),
                entity.getPhotoUrl()
        );
    }
}
