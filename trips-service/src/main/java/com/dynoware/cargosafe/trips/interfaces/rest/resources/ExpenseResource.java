package com.dynoware.cargosafe.trips.interfaces.rest.resources;

public record ExpenseResource(Long id, int fuelAmount, String fuelDescription, int viaticsAmount, String viaticsDescription, int tollsAmount, String tollsDescription) {
}
