package com.dynoware.cargosafe.tripsservice.trips.domain.services;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.DeleteVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateVehicleCommand;

public interface VehicleCommandService {
    void createVehicle(CreateVehicleCommand command);
    void updateVehicle(UpdateVehicleCommand command);
    void deleteVehicle(DeleteVehicleCommand command);
    void handle(CreateVehicleCommand command);
}