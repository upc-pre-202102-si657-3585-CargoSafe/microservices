package com.dynoware.cargosafe.requestservice.request.application.internal.commandservices;

import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.CreateRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.DeleteRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.UpdateRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.entities.RequestServiceStatus;
import com.dynoware.cargosafe.requestservice.request.domain.model.entities.Status;
import com.dynoware.cargosafe.requestservice.request.domain.services.RequestServiceCommandService;
import com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories.RequestServiceRepository;
import com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories.StatusRepository;
import com.dynoware.cargosafe.requestservice.request.domain.exceptions.RequestServiceException;
import com.dynoware.cargosafe.requestservice.request.domain.exceptions.StatusNotFoundException;
import com.dynoware.cargosafe.requestservice.request.domain.exceptions.RequestServiceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceCommandServiceImpl implements RequestServiceCommandService {
    private final RequestServiceRepository repository;
    private final StatusRepository statusRepository;

    public RequestServiceCommandServiceImpl(RequestServiceRepository repository, StatusRepository statusRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
    }

    @Override
    public RequestService handle(CreateRequestServiceCommand command) {
        var requestService = new RequestService();
        requestService.setUnloadDirection(command.unloadDirection());
        requestService.setType(command.type());
        requestService.setNumberPackages(command.numberPackages());
        requestService.setCountry(command.country());
        requestService.setDepartment(command.department());
        requestService.setDistrict(command.district());
        requestService.setDestination(command.destination());
        requestService.setUnloadLocation(command.unloadLocation());
        requestService.setUnloadDate(command.unloadDate());
        requestService.setDistance(command.distance());
        requestService.setHolderName(command.holderName());
        requestService.setPickupAddress(command.pickupAddress());
        requestService.setPickupLat(command.pickupLat());
        requestService.setPickupLng(command.pickupLng());
        requestService.setDestinationAddress(command.destinationAddress());
        requestService.setDestinationLat(command.destinationLat());
        requestService.setDestinationLng(command.destinationLng());
        requestService.setLoadDetail(command.loadDetail());
        requestService.setWeight(command.weight());
        requestService.setUserId(command.userId());

        Long statusId = command.statusId() != null ? command.statusId() : 3L;
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + statusId));
        requestService.setStatus(status);

        RequestServiceStatus requestServiceStatus = new RequestServiceStatus();
        requestServiceStatus.setRequestService(requestService);
        requestServiceStatus.setStatus(status);
        requestService.getStatuses().add(requestServiceStatus);

        repository.save(requestService);
        return requestService;
    }

    @Override
    public RequestService handle(UpdateRequestServiceCommand command) {
        var requestService = repository.findById(command.id())
                .orElseThrow(() -> new RequestServiceNotFoundException("RequestService not found with id: " + command.id()));

        Status status = statusRepository.findById(command.statusId())
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + command.statusId()));
        requestService.setStatus(status);
        repository.save(requestService);
        return requestService;
    }

    @Override
    public void handle(DeleteRequestServiceCommand command) {
        repository.deleteById(command.id());
    }

    public RequestService updateStatus(RequestService requestService, Long statusId) {
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + statusId));
        requestService.setStatus(status);

        for (RequestServiceStatus requestServiceStatus : requestService.getStatuses()) {
            requestServiceStatus.setStatus(status);
        }

        repository.save(requestService);
        return requestService;
    }
}