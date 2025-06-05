package com.dynoware.cargosafe.tripsservice.trips.application.internal.commandservices;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Evidence;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateEvidenceCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.EvidenceCommandService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.repositories.EvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvidenceCommandServiceImpl implements EvidenceCommandService {
    private final EvidenceRepository evidenceRepository;

    public EvidenceCommandServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public Optional<Evidence> handle(CreateEvidenceCommand command) {
        if (evidenceRepository.existsByTripId(command.tripId())) {
            throw new IllegalArgumentException("An evidence with that link already exists");
        }
        var evidence = new Evidence(command);
        try {
            evidenceRepository.save(evidence);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating a new evidence");
        }

        return Optional.of(evidence);
    }
}
