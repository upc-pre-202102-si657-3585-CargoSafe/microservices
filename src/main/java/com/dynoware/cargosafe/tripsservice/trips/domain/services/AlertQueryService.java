package com.dynoware.cargosafe.trips.domain.services;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAlertByIdQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllAlertQuery;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {

    Optional<Alert> handle(GetAlertByIdQuery query);

    List<Alert> handle(GetAllAlertQuery query);
}
