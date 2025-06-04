package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.EvidenceResource;

public class EvidenceResourceFromEntityAssembler {
    public static EvidenceResource transformResourceFromEntity(Evidence entity) {
        return new EvidenceResource(
                entity.getId(),
                entity.getLink(),
                entity.getTripId()
        );
    }
}
