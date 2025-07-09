package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.DeleteVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.UpdateVehicleCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllVehiclesQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetVehicleByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.VehicleCommandService;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.VehicleQueryService;
import com.dynoware.cargosafe.tripsservice.trips.infrastructure.persistence.jpa.repositories.VehicleRepository;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateVehicleResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.UpdateVehicleResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.VehicleResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.UpdateVehicleCommandFromResourceAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Vehicles", description = "Vehicles Management Endpoints")
public class VehicleController {
    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;
    private final VehicleRepository vehicleRepository;
    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService, VehicleRepository vehicleRepository) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        var query = new GetAllVehiclesQuery();
        var vehicles = vehicleQueryService.handle(query);
        var vehicleResources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::transformResourceFromEntity)
                .toList();
        return ResponseEntity.ok(vehicleResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long id) {
        var query = new GetVehicleByIdQuery(id);
        var vehicle = vehicleQueryService.handle(query);
        return vehicle.map(value -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.transformResourceFromEntity(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new vehicle", description = "Create a new vehicle with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<VehicleResource> createVehicle(@Valid @RequestBody CreateVehicleResource resource) {
        var createVehicleCommand = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        vehicleCommandService.createVehicle(createVehicleCommand);
        var vehicle = vehicleRepository.findByModel(createVehicleCommand.model());
        if (vehicle.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        var vehicleResource = VehicleResourceFromEntityAssembler.transformResourceFromEntity(vehicle.get());
        return new ResponseEntity<>(vehicleResource, HttpStatus.CREATED);
    }


    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable Long vehicleId, @RequestBody UpdateVehicleResource resource) {
        var updateVehicleCommand = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(vehicleId, resource);
        vehicleCommandService.updateVehicle(updateVehicleCommand);
        var updatedVehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId));
        if (updatedVehicle.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var vehicleResource = VehicleResourceFromEntityAssembler.transformResourceFromEntity(updatedVehicle.get());
        return ResponseEntity.ok(vehicleResource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        var command = new DeleteVehicleCommand(id);
        vehicleCommandService.deleteVehicle(command);
        return ResponseEntity.noContent().build();
    }
}