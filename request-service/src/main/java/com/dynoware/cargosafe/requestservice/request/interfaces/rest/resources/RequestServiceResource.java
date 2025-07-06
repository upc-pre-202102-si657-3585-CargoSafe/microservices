package com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources;


public record RequestServiceResource(
        Long id,
        String unloadDirection,
        String type,
        int numberPackages,
        String country,
        String department,
        String district,
        String destination,
        String unloadLocation,
        String unloadDate,
        Double distance,
        String status,
        String holderName,
        String pickupAddress,
        double pickupLat,
        double pickupLng,
        String destinationAddress,
        double destinationLat,
        double destinationLng,
        String loadDetail,
        String weight,
        Long userId
) {
}