package com.dynoware.cargosafe.trips.domain.services;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllDriversQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetDriverByIdQuery;

import java.util.List;
import java.util.Optional;

public interface DriverQueryService {
    Optional<Driver> handle(GetDriverByIdQuery query);

    List<Driver> handle(GetAllDriversQuery query);
}
