package com.dynoware.cargosafe.trips.domain.services;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateAlertCommand;

import java.util.Optional;

public interface AlertCommandService {

    Optional<Alert> handle(CreateAlertCommand command);
}
