package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert entity) {
        return new AlertResource(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDate());
    }
}