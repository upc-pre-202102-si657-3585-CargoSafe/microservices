package com.dynoware.cargosafe.tripsservice.trips.application.internal.commandservices;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.DeleteVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.VehicleCommandService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.repositories.VehicleRepository;
import com.dynoware.cargosafe.tripsservice.trips.domain.exceptions.VehicleAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {
    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void createVehicle(CreateVehicleCommand command) {
        if (command.model() == null || command.model().isBlank())
            throw new IllegalArgumentException("Model is required");
        if (command.plate() == null || command.plate().isBlank())
            throw new IllegalArgumentException("Plate is required");
        if (command.maxLoad() == null || command.maxLoad() <= 0)
            throw new IllegalArgumentException("Max load must be positive");
        if (command.volume() == null || command.volume() <= 0)
            throw new IllegalArgumentException("Volume must be positive");
        if (command.photoUrl() == null || command.photoUrl().isBlank())
            throw new IllegalArgumentException("Photo URL is required");
        if (vehicleRepository.existsByModel(command.model())) {
            throw new VehicleAlreadyExistsException("A vehicle with this model already exists");
        }
        if (vehicleRepository.existsByPlate(command.plate())) {
            throw new VehicleAlreadyExistsException("A vehicle with this plate already exists");
        }
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