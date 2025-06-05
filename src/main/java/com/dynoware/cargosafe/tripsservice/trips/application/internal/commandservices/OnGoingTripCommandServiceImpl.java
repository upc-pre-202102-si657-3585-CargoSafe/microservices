package com.dynoware.cargosafe.tripsservice.trips.application.internal.commandservices;


import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.OnGoingTrip;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateOnGoingTripCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateOnGoingTripCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.OnGoingTripCommandService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.OnGoingTripRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnGoingTripCommandServiceImpl implements OnGoingTripCommandService {
    private final OnGoingTripRepository onGoingTripRepository;

    public OnGoingTripCommandServiceImpl(OnGoingTripRepository onGoingTripRepository) {
        this.onGoingTripRepository = onGoingTripRepository;
    }

    @Override
    public Long handle(CreateOnGoingTripCommand command) {
        if (onGoingTripRepository.existsByDistance(command.distance())) {
            throw new IllegalArgumentException("On going trip with the same distance already exists");
        }
        var onGoingTrip = new OnGoingTrip(command);
        try {
            onGoingTripRepository.save(onGoingTrip);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving on going trip :" + e.getMessage());
        }
        return onGoingTrip.getId();
    }

    @Override
    public Optional<OnGoingTrip> handle(UpdateOnGoingTripCommand command) {
        if (onGoingTripRepository.existsByDistanceAndIdIsNot(command.distance(),command.id()))
            throw new IllegalArgumentException("On going trip with same distance distance already exists");
        var result = onGoingTripRepository.findById(command.id());
        if (result.isEmpty())
            throw new IllegalArgumentException("On going trip does not exist");
        var onGoingTripToUpdate = result.get();
        try {
            var updatedOnGoingTrip = onGoingTripRepository.save(onGoingTripToUpdate.updateInformation(command.latitude(), command.longitude(), command.speed(), command.distance()));
            return Optional.of(updatedOnGoingTrip);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating on going trip: " + e.getMessage());
        }
    }
}
