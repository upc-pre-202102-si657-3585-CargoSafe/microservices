package com.dynoware.cargosafe.trips.infrastructure.persistence.jpa;

import com.dynoware.cargosafe.trips.domain.model.aggregates.OnGoingTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnGoingTripRepository extends JpaRepository<OnGoingTrip, Long> {
    Optional<OnGoingTrip> findByDistance(Integer distance);
    boolean existsByDistanceAndIdIsNot(Integer distance, Long id);

    boolean existsByDistance(Integer distance);
}
