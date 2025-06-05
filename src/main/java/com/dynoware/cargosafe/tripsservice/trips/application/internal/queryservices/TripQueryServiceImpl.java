package com.dynoware.cargosafe.tripsservice.trips.application.internal.queryservices;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Trip;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllTripsByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllTripsByUserIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllTripsQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.TripQueryService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.repositories.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripQueryServiceImpl implements TripQueryService {
    private final TripRepository tripRepository;

    public TripQueryServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Optional<Trip> handle(GetAllTripsByIdQuery query) {
        return tripRepository.findById(query.Id());
    }

    @Override
    public List<Trip> handle(GetAllTripsByUserIdQuery query) {
        return tripRepository.findByUserId(query.userId());
    }

    @Override
    public List<Trip> handle(GetAllTripsQuery query) {
        return tripRepository.findAll();
    }
}