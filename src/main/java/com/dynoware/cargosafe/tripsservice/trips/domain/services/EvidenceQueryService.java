package com.dynoware.cargosafe.trips.domain.services;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllEvidencesQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetEvidenceByIdQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetEvidenceByTripIdQuery;

import java.util.List;
import java.util.Optional;

public interface EvidenceQueryService {
    Optional<Evidence> handle(GetEvidenceByIdQuery query);
    Optional<Evidence> handle(GetEvidenceByTripIdQuery query);
    List<Evidence> handle(GetAllEvidencesQuery query);
}
