package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateExpenseCommand;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateExpenseResource;

public class CreateExpenseCommandFromResourceAssembler {
    public static CreateExpenseCommand toCommandFromResource(CreateExpenseResource resource) {
        return new CreateExpenseCommand(resource.fuelAmount(), resource.fuelDescription(), resource.viaticsAmount(), resource.viaticsDescription(), resource.tollsAmount(), resource.tollsDescription());
    }
}