package com.dynoware.cargosafe.tripsservice.trips.domain.model.queries;

public record GetEvidenceByTripIdQuery(Long tripId) {
    public GetEvidenceByTripIdQuery {
        if (tripId == null) {
            throw new IllegalArgumentException("Trip ID cannot be null");
        }
    }
}
