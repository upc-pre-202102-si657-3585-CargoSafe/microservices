package com.dynoware.cargosafe.trips.interfaces.rest.resources;

public record CreateExpenseResource(int fuelAmount, String fuelDescription, int viaticsAmount, String viaticsDescription, int tollsAmount, String tollsDescription) {
    public CreateExpenseResource {
        if (fuelAmount <= 0) {
            throw new IllegalArgumentException("Fuel amount must be greater than 0");
        }
        if (fuelDescription == null || fuelDescription.isBlank()) {
            throw new IllegalArgumentException("Fuel description cannot be null or empty");
        }
        if (viaticsAmount <= 0) {
            throw new IllegalArgumentException("Viatics amount must be greater than 0");
        }
        if (viaticsDescription == null || viaticsDescription.isBlank()) {
            throw new IllegalArgumentException("Viatics description cannot be null or empty");
        }
        if (tollsAmount <= 0) {
            throw new IllegalArgumentException("Tolls amount must be greater than 0");
        }
        if (tollsDescription == null || tollsDescription.isBlank()) {
            throw new IllegalArgumentException("Tolls description cannot be null or empty");
        }
    }
}
