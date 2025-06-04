package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.VehicleResource;

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
