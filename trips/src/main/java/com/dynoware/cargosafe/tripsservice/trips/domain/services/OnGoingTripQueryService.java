package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.OnGoingTrip;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllOnGoingTripsQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetOnGoingTripByIdQuery;

import java.util.List;
import java.util.Optional;

public interface OnGoingTripQueryService {
    Optional<OnGoingTrip> handle(GetOnGoingTripByIdQuery query);
    List<OnGoingTrip> handle(GetAllOnGoingTripsQuery query);
}
