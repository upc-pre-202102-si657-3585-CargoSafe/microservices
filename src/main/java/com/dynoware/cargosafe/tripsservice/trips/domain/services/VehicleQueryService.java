package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllVehiclesQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetVehicleByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetVehicleByModelQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
    Optional<Vehicle> handle(GetVehicleByModelQuery query);
    List<Vehicle> handle(GetAllVehiclesQuery query);
}
