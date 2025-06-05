package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateAlertCommand;

import java.util.Optional;

public interface AlertCommandService {

    Optional<Alert> handle(CreateAlertCommand command);
}
