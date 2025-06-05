package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest;


import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.DeleteDriverCommand;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllDriversQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetDriverByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.DriverCommandService;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.DriverQueryService;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateDriverResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.DriverResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.UpdateDriverResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.CreateDriverCommandFromResourceAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.DriverResourceFromEntityAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.UpdateDriverCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/drivers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Drivers", description = "Driver Management Endpoints")
public class DriverController {
    private final DriverCommandService driverCommandService;
    private final DriverQueryService driverQueryService;

    public DriverController(DriverCommandService driverCommandService, DriverQueryService driverQueryService) {
        this.driverCommandService = driverCommandService;
        this.driverQueryService = driverQueryService;
    }

    @PostMapping
    public ResponseEntity<DriverResource> createDriver(@RequestBody CreateDriverResource createDriverResource) {
        var createDriverCommand = CreateDriverCommandFromResourceAssembler.toCommandFromResource(createDriverResource);
        var driverId = driverCommandService.handle(createDriverCommand);
        if (driverId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getDriverByIdQuery = new GetDriverByIdQuery(driverId);
        var driver = driverQueryService.handle(getDriverByIdQuery);
        if (driver.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var driverResource = DriverResourceFromEntityAssembler.toResourceFromEntity(driver.get());
        return new ResponseEntity<>(driverResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DriverResource>> getAllDrivers() {
        var getAllDriversQuery = new GetAllDriversQuery();
        var drivers = driverQueryService.handle(getAllDriversQuery);
        var driverResources = drivers.stream().map(DriverResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(driverResources);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResource> updateDriver(@PathVariable Long driverId, @RequestBody UpdateDriverResource updateDriverResource) {
        var updateDriverCommand = UpdateDriverCommandFromResourceAssembler.toCommandFromResource(driverId, updateDriverResource);
        var updateDriver = driverCommandService.handle(updateDriverCommand);
        if (updateDriver.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var driverResource = DriverResourceFromEntityAssembler.toResourceFromEntity(updateDriver.get());
        return ResponseEntity.ok(driverResource);
    }

    @DeleteMapping("{driverId}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long driverId) {
        var deleteDriverCommand = new DeleteDriverCommand(driverId);
        driverCommandService.handle(deleteDriverCommand);
        return ResponseEntity.ok("Driver deleted successfully");
    }
}
