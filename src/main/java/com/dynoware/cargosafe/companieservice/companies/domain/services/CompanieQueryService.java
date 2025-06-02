package com.dynoware.cargosafe.companieservice.companies.domain.services;

import com.dynoware.cargosafe.companieservice.companies.domain.model.aggregates.Companie;
import com.dynoware.cargosafe.companieservice.companies.domain.model.queries.GetCompanieByIdQuery;

import java.util.Optional;

public interface CompanieQueryService {
    Optional<Companie> handle(GetCompanieByIdQuery query);
}
