package com.dynoware.cargosafe.trips.application.internal.commandservices;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Trip;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateTripCommand;
import com.dynoware.cargosafe.trips.domain.model.commands.DeleteTripCommand;
import com.dynoware.cargosafe.trips.domain.model.commands.UpdateTripCommand;
import com.dynoware.cargosafe.trips.domain.services.TripCommandService;
import com.dynoware.cargosafe.trips.infrastructure.persistence.jpa.repositories.TripRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TripCommandServiceImpl implements TripCommandService {
    private final TripRepository tripRepository;

    public TripCommandServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Optional<Trip> handle(CreateTripCommand command) {
        var trip = new Trip(command);
        tripRepository.save(trip);
        return Optional.of(trip);
    }

    @Override
    public Optional<Trip> handle(UpdateTripCommand command) {
        var trip = tripRepository.findById(command.id());
        if (trip.isPresent()) {
            trip.get().updateTrip(command);
            tripRepository.save(trip.get());
        }
        return trip;
    }

    @Override
    public void handle(DeleteTripCommand command) {
        if (tripRepository.existsById(command.Id())) {
            tripRepository.deleteById(command.Id());
        } else {
            throw new IllegalArgumentException("Trip not found");
        }
    }
}