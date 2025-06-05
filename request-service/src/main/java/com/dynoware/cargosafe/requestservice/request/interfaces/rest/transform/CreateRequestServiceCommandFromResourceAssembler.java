package com.dynoware.cargosafe.requestservice.request.interfaces.rest.transform;


import com.dynoware.cargosafe.requestservice.request.domain.model.commands.CreateRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources.CreateRequestServiceResource;

public class CreateRequestServiceCommandFromResourceAssembler {
    public static CreateRequestServiceCommand toCommandFromResource(CreateRequestServiceResource resource) {
        return new CreateRequestServiceCommand(
                resource.unloadDirection(),
                resource.type(),
                resource.numberPackages(),
                resource.country(),
                resource.department(),
                resource.district(),
                resource.destination(),
                resource.unloadLocation(),
                resource.unloadDate(),
                resource.distance(),
                resource.statusId(),
                resource.holderName(),
                resource.pickupAddress(),
                resource.pickupLat(),
                resource.pickupLng(),
                resource.destinationAddress(),
                resource.destinationLat(),
                resource.destinationLng(),
                resource.loadDetail(),
                resource.weight()
        );
    }
}