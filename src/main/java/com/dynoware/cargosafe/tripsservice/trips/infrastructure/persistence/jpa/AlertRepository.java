package com.dynoware.cargosafe.trips.infrastructure.persistence.jpa;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findById(Long id);

    List<Alert> findAll();

}
