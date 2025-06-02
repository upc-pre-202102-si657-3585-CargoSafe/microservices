package com.dynoware.cargosafe.trips.interfaces.rest;

import com.dynoware.cargosafe.trips.domain.model.queries.GetAllEvidencesQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetEvidenceByIdQuery;
import com.dynoware.cargosafe.trips.domain.services.EvidenceCommandService;
import com.dynoware.cargosafe.trips.domain.services.EvidenceQueryService;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.CreateEvidenceResource;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.EvidenceResource;
import com.dynoware.cargosafe.trips.interfaces.rest.transform.CreateEvidenceCommandFromResourceAssembler;
import com.dynoware.cargosafe.trips.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/evidence")
@Tag(name = "Evidence", description = "Evidence Management Endpoints")
public class EvidenceController {
    private final EvidenceQueryService evidenceQueryService;
    private final EvidenceCommandService evidenceCommandService;

    public EvidenceController(EvidenceQueryService evidenceQueryService, EvidenceCommandService evidenceCommandService) {
        this.evidenceQueryService = evidenceQueryService;
        this.evidenceCommandService = evidenceCommandService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvidenceResource> getEvidenceById(@PathVariable Long id) {
        var getEvidenceByIdQuery = new GetEvidenceByIdQuery(id);
        var evidence = evidenceQueryService.handle(getEvidenceByIdQuery);
        if (evidence.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(EvidenceResourceFromEntityAssembler.transformResourceFromEntity(evidence.get()));
    }

    @GetMapping
    public ResponseEntity<List<EvidenceResource>> getAllEvidences() {
        var getAllEvidencesQuery = new GetAllEvidencesQuery();
        var evidences = evidenceQueryService.handle(getAllEvidencesQuery);
        var evidencesResource = evidences.stream().map(EvidenceResourceFromEntityAssembler::transformResourceFromEntity).toList();
        return ResponseEntity.ok(evidencesResource);
    }

    @PostMapping
    public ResponseEntity<EvidenceResource> createEvidence(@RequestBody CreateEvidenceResource resource) {
        var createEvidenceCommand = CreateEvidenceCommandFromResourceAssembler.toCommandFromResource(resource);
        var evidence = evidenceCommandService.handle(createEvidenceCommand);
        if (evidence.isEmpty()) return ResponseEntity.notFound().build();
        var evidenceResource = EvidenceResourceFromEntityAssembler.transformResourceFromEntity(evidence.get());
        return ResponseEntity.ok(evidenceResource);
    }
}
