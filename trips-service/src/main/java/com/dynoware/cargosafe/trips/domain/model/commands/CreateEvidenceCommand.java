package com.dynoware.cargosafe.trips.domain.model.commands;

import jakarta.validation.constraints.NotBlank;

public record CreateEvidenceCommand(
        @NotBlank String link,
        @NotBlank Long tripId
) {
}
