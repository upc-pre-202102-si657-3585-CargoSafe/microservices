package com.dynoware.cargosafe.trips.domain.services;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateEvidenceCommand;

import java.util.Optional;

public interface EvidenceCommandService {
    Optional<Evidence> handle (CreateEvidenceCommand command);
}
