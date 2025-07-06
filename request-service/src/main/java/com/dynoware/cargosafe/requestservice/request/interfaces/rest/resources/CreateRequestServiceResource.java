package com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateRequestServiceResource(
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
        Long statusId,
        String holderName,
        String pickupAddress,
        double pickupLat,
        double pickupLng,
        String destinationAddress,
        double destinationLat,
        double destinationLng,
        String loadDetail,
        String weight,
        @NotNull Long userId
) {
}