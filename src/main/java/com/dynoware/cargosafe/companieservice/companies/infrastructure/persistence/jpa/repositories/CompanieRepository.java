package com.dynoware.cargosafe.companieservice.companies.infrastructure.persistence.jpa.repositories;

import com.dynoware.cargosafe.companieservice.companies.domain.model.aggregates.Companie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanieRepository extends JpaRepository<Companie, Long> {
    Optional<Companie> findById(Long id);
}
