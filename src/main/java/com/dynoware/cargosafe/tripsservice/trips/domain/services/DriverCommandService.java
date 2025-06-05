package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateDriverCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.DeleteDriverCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateDriverCommand;

import java.util.Optional;

public interface DriverCommandService {
    Long handle(CreateDriverCommand command);
    Optional<Driver> handle(UpdateDriverCommand command);

    void handle(DeleteDriverCommand command);
}
