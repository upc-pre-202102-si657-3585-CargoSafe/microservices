package com.dynoware.cargosafe.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.trips.domain.model.commands.CreateTripCommand;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.CreateTripResource;

public class CreateTripCommandFromResourceAssembler {
    public static CreateTripCommand toCommandFromResource(CreateTripResource resource) {
        return new CreateTripCommand(
                resource.name(),
                resource.type(),
                resource.weight(),
                resource.unloadDirection(),
                resource.unloadLocation(),
                resource.unloadDate(),
                resource.expenseId(),
                resource.alertId(),
                resource.ongoingTripId(),
                resource.vehicleId(),
                resource.driverId(),
                resource.userId(),
                resource.destination(),
                resource.department(),
                resource.district(),
                resource.country(),
                resource.numberPackages(),
                resource.holderName(),
                resource.destinationDate(),
                resource.totalAmount(),
                resource.destinationAddress(),
                resource.loadDetail(),
                resource.pickupAddress()
        );
    }
}