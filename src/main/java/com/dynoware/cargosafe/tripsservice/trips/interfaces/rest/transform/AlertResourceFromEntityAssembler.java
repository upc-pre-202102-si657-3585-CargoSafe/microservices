package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert entity) {
        return new AlertResource(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDate());
    }
}