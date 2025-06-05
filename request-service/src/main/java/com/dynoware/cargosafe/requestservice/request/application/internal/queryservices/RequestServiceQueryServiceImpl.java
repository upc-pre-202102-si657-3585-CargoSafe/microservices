package com.dynoware.cargosafe.requestservice.request.application.internal.queryservices;

import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.domain.model.queries.GetAllRequestServiceQuery;
import com.dynoware.cargosafe.requestservice.request.domain.model.queries.GetRequestServiceByIdQuery;
import com.dynoware.cargosafe.requestservice.request.domain.services.RequestServiceQueryService;
import com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories.RequestServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceQueryServiceImpl implements RequestServiceQueryService {
    private final RequestServiceRepository repository;

    public RequestServiceQueryServiceImpl(RequestServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RequestService> handle(GetRequestServiceByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<RequestService> handle(GetAllRequestServiceQuery query) {
        return repository.findAll();
    }
}