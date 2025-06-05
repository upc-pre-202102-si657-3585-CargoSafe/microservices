package com.dynoware.cargosafe.companieservice.companies.application.internal.queryservices;

import com.dynoware.cargosafe.companieservice.companies.domain.model.aggregates.Companie;
import com.dynoware.cargosafe.companieservice.companies.domain.model.queries.GetCompanieByIdQuery;
import com.dynoware.cargosafe.companieservice.companies.domain.services.CompanieQueryService;
import com.dynoware.cargosafe.companieservice.companies.infrastructure.persistence.jpa.repositories.CompanieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanieQueryServiceImpl implements CompanieQueryService {
    private final CompanieRepository repository;

    public CompanieQueryServiceImpl(CompanieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Companie> handle(GetCompanieByIdQuery query) {
        return repository.findById(query.id());
    }
}
