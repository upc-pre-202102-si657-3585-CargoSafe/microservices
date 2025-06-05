package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.OnGoingTrip;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateOnGoingTripCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateOnGoingTripCommand;

import java.util.Optional;

public interface OnGoingTripCommandService {
    Long handle(CreateOnGoingTripCommand command);
    Optional<OnGoingTrip> handle(UpdateOnGoingTripCommand command);
}
