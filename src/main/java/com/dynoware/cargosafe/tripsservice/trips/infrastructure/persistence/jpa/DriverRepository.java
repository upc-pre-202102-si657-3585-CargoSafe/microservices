package com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByName(String name);
    boolean existsByNameAndIdIsNot(String name, Long id);

    boolean existsByName(String name);
}
