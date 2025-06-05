package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record CreateExpenseCommand(int fuelAmount, String fuelDescription, int viaticsAmount, String viaticsDescription, int tollsAmount, String tollsDescription) {
    public CreateExpenseCommand {
        if (fuelAmount < 0 || viaticsAmount < 0 || tollsAmount < 0)
            throw new IllegalArgumentException("amounts cannot be negative");
        if (fuelDescription == null || fuelDescription.isBlank())
            throw new IllegalArgumentException("fuel_description cannot be null or empty");
        if (viaticsDescription == null || viaticsDescription.isBlank())
            throw new IllegalArgumentException("viatics_description cannot be null or empty");
        if (tollsDescription == null || tollsDescription.isBlank())
            throw new IllegalArgumentException("tolls_description cannot be null or empty");
    }

}
