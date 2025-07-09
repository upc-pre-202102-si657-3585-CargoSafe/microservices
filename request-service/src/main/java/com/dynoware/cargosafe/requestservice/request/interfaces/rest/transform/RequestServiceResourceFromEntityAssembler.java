package com.dynoware.cargosafe.requestservice.request.interfaces.rest.transform;


import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources.RequestServiceResource;

public class RequestServiceResourceFromEntityAssembler {
    public static RequestServiceResource transformResourceFromEntity(RequestService entity) {
        return new RequestServiceResource(
                entity.getId(),
                entity.getUnloadDirection(),
                entity.getType(),
                entity.getNumberPackages(),
                entity.getCountry(),
                entity.getDepartment(),
                entity.getDistrict(),
                entity.getDestination(),
                entity.getUnloadLocation(),
                entity.getUnloadDate(),
                entity.getDistance(),
                entity.getStatus().getName().name(),
                entity.getHolderName(),
                entity.getPickupAddress(),
                entity.getPickupLat(),
                entity.getPickupLng(),
                entity.getDestinationAddress(),
                entity.getDestinationLat(),
                entity.getDestinationLng(),
                entity.getLoadDetail(),
                entity.getWeight(),
                entity.getUserId()
        );
    }
}