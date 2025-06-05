package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.EvidenceResource;

public class EvidenceResourceFromEntityAssembler {
    public static EvidenceResource transformResourceFromEntity(Evidence entity) {
        return new EvidenceResource(
                entity.getId(),
                entity.getLink(),
                entity.getTripId()
        );
    }
}
