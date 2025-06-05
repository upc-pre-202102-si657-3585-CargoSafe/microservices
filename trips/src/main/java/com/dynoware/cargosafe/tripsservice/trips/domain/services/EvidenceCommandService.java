package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateEvidenceCommand;

import java.util.Optional;

public interface EvidenceCommandService {
    Optional<Evidence> handle (CreateEvidenceCommand command);
}
