package com.dynoware.cargosafe.trips.application.internal.queryservices;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllDriversQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetDriverByIdQuery;
import com.dynoware.cargosafe.trips.domain.services.DriverQueryService;
import com.dynoware.cargosafe.trips.infrastructure.persistence.jpa.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverQueryServiceImpl implements DriverQueryService {
    private final DriverRepository driverRepository;

    public DriverQueryServiceImpl(DriverRepository driverRepository){
        this.driverRepository = driverRepository;
    }

    @Override
    public Optional<Driver> handle(GetDriverByIdQuery query){
        return this.driverRepository.findById(query.DriverId());
    }

    @Override
    public List<Driver> handle(GetAllDriversQuery query) {
        return this.driverRepository.findAll();
    }
}
