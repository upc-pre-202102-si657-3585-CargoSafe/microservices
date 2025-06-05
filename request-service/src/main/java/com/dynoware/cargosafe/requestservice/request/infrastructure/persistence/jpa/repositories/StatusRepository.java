package com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories;

import com.dynoware.cargosafe.requestservice.request.domain.model.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}