package com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories;

import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestServiceRepository  extends JpaRepository<RequestService, Long> {
    List<RequestService> findByUserId(Long userId);
}