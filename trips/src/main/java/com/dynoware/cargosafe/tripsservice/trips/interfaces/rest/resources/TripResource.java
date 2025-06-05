package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources;

public record TripResource(
        Long id,
        String name,
        String type,
        double weight,
        String unloadDirection,
        String unloadLocation,
        String unloadDate,
        Long expenseId,
        Long alertId,
        Long ongoingTripId,
        Long vehicleId,
        Long driverId,
        Long userId,
        String destination,
        String department,
        String district,
        String country,
        int numberPackages,
        String holderName,
        String destinationDate,
        double totalAmount,
        String destinationAddress,
        String loadDetail,
        String pickupAddress
) {
}