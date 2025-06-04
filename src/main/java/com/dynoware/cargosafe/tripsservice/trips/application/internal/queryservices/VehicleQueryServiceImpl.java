package com.dynoware.cargosafe.trips.application.internal.queryservices;

import com.dynoware.cargosafe.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllVehiclesQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetVehicleByIdQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetVehicleByModelQuery;
import com.dynoware.cargosafe.trips.domain.services.VehicleQueryService;
import com.dynoware.cargosafe.trips.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {
    private final VehicleRepository vehicleRepository;
    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return this.vehicleRepository.findById(query.id());
    }

    @Override
    public List<Vehicle> handle(GetAllVehiclesQuery query) {
        return this.vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByModelQuery query) {
        return this.vehicleRepository.findByModel(query.model());
    }
}
