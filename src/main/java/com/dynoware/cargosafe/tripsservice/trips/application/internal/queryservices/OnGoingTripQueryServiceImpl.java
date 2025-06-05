package com.dynoware.cargosafe.tripsservice.trips.application.internal.queryservices;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.OnGoingTrip;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllOnGoingTripsQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetOnGoingTripByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.OnGoingTripQueryService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.OnGoingTripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnGoingTripQueryServiceImpl implements OnGoingTripQueryService {
    private final OnGoingTripRepository onGoingTripRepository;

    public OnGoingTripQueryServiceImpl(OnGoingTripRepository onGoingTripRepository) {
        this.onGoingTripRepository = onGoingTripRepository;
    }

    @Override
    public Optional<OnGoingTrip> handle(GetOnGoingTripByIdQuery query) {
        return onGoingTripRepository.findById(query.OnGoingTripId());
    }

    @Override
    public List<OnGoingTrip> handle(GetAllOnGoingTripsQuery query) {
        return onGoingTripRepository.findAll();
    }
}
