package com.dynoware.cargosafe.requestservice.request.application.acl;

import com.dynoware.cargosafe.requestservice.kafka.producer.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories.RequestServiceRepository;
import com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories.StatusRepository;
import com.dynoware.cargosafe.requestservice.request.interfaces.acl.RequestServicesContextFacade;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RequestContextFacadeImpl implements RequestServicesContextFacade {
    private final RequestServiceRepository repository;
    private final StatusRepository statusRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RequestContextFacadeImpl(RequestServiceRepository repository, StatusRepository statusRepository, KafkaProducerService kafkaProducerService) {
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public RequestService createRequestService(RequestService requestService) {
        RequestService saved = repository.save(requestService);
        kafkaProducerService.sendEvent("request-service-created", saved);
        return saved;
    }

    @Override
    public RequestService updateRequestService(Long id, RequestService requestService) {
        RequestService existing = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RequestService not found"));

            existing.setUnloadDirection(requestService.getUnloadDirection());
        existing.setType(requestService.getType());
        existing.setNumberPackages(requestService.getNumberPackages());
        existing.setCountry(requestService.getCountry());
        existing.setDepartment(requestService.getDepartment());
        existing.setDistrict(requestService.getDistrict());
        existing.setDestination(requestService.getDestination());
        existing.setUnloadLocation(requestService.getUnloadLocation());
        existing.setUnloadDate(requestService.getUnloadDate());
        existing.setDistance(requestService.getDistance());
        existing.setHolderName(requestService.getHolderName());
        existing.setPickupAddress(requestService.getPickupAddress());
        existing.setPickupLat(requestService.getPickupLat());
        existing.setPickupLng(requestService.getPickupLng());
        existing.setDestinationAddress(requestService.getDestinationAddress());
        existing.setDestinationLat(requestService.getDestinationLat());
        existing.setDestinationLng(requestService.getDestinationLng());
        existing.setLoadDetail(requestService.getLoadDetail());
        existing.setWeight(requestService.getWeight());
        existing.setStatus(requestService.getStatus());
        return repository.save(existing);
    }

    @Override
    public RequestService updateRequestServiceStatus(Long id, Long statusId) {
        RequestService requestService = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RequestService not found"));
        var status = statusRepository.findById(statusId)
            .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        requestService.setStatus(status);
        return repository.save(requestService);
    }

    @Override
    public Optional<RequestService> getRequestServiceById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<RequestService> getAllRequestServices() {
        return repository.findAll();
    }

    @Override
    public void deleteRequestService(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<RequestService> getRequestServicesByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
