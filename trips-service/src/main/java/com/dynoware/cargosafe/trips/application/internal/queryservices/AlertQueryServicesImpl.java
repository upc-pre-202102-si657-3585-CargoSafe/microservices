package com.dynoware.cargosafe.trips.application.internal.queryservices;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAlertByIdQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllAlertQuery;
import com.dynoware.cargosafe.trips.domain.services.AlertQueryService;
import com.dynoware.cargosafe.trips.infrastructure.persistence.jpa.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertQueryServicesImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    public AlertQueryServicesImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.id());
    }

    @Override
    public List<Alert> handle(GetAllAlertQuery query){
        return alertRepository.findAll();
    }

}
