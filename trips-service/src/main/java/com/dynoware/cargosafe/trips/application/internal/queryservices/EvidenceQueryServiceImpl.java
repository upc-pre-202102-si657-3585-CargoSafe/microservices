package com.dynoware.cargosafe.trips.application.internal.queryservices;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllEvidencesQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetEvidenceByIdQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetEvidenceByTripIdQuery;
import com.dynoware.cargosafe.trips.domain.services.EvidenceQueryService;
import com.dynoware.cargosafe.trips.infrastructure.persistence.jpa.repositories.EvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvidenceQueryServiceImpl implements EvidenceQueryService {
    private final EvidenceRepository evidenceRepository;
    public EvidenceQueryServiceImpl(EvidenceRepository evidenceRepository) { this.evidenceRepository = evidenceRepository; }

    @Override
    public Optional<Evidence> handle(GetEvidenceByIdQuery query) { return this.evidenceRepository.findById(query.id()); }

    @Override
    public List<Evidence> handle(GetAllEvidencesQuery query) { return this.evidenceRepository.findAll(); }

    @Override
    public Optional<Evidence> handle(GetEvidenceByTripIdQuery query) { return this.evidenceRepository.findByTripId(query.tripId()); }
}
