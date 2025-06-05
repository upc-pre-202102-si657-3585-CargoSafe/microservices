package com.dynoware.cargosafe.tripsservice.trips.application.internal.commandservices;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.DeleteVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.VehicleCommandService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {
    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void createVehicle(CreateVehicleCommand command) {
        Vehicle vehicle = new Vehicle(command.model(), command.plate(), command.maxLoad(), command.volume(), command.photoUrl());
        vehicleRepository.save(vehicle);
    }

    @Override
    public void handle(CreateVehicleCommand command) {
        createVehicle(command);
    }

    @Override
    public void updateVehicle(UpdateVehicleCommand command) {
        Vehicle vehicle = vehicleRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setModel(command.model());
        vehicle.setPlate(command.plate());
        vehicle.setMaxLoad(command.maxLoad());
        vehicle.setVolume(command.volume());
        vehicle.setPhotoUrl(command.photoUrl());
        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(DeleteVehicleCommand command) {
        vehicleRepository.deleteById(command.id());
    }
}