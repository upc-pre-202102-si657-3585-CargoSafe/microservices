package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Trip;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllTripsByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllTripsByUserIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllTripsQuery;

import java.util.List;
import java.util.Optional;

public interface TripQueryService {
    Optional<Trip> handle(GetAllTripsByIdQuery query);
    List<Trip> handle(GetAllTripsByUserIdQuery query);
    List<Trip> handle(GetAllTripsQuery query);
}