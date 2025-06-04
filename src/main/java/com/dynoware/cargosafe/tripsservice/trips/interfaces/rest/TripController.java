package com.dynoware.cargosafe.trips.interfaces.rest;

import com.dynoware.cargosafe.trips.domain.model.commands.DeleteTripCommand;
import com.dynoware.cargosafe.trips.domain.model.commands.UpdateTripCommand;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllTripsByIdQuery;
import com.dynoware.cargosafe.trips.domain.model.queries.GetAllTripsQuery;
import com.dynoware.cargosafe.trips.domain.services.TripCommandService;
import com.dynoware.cargosafe.trips.domain.services.TripQueryService;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.CreateTripResource;
import com.dynoware.cargosafe.trips.interfaces.rest.resources.TripResource;
import com.dynoware.cargosafe.trips.interfaces.rest.transform.CreateTripCommandFromResourceAssembler;
import com.dynoware.cargosafe.trips.interfaces.rest.transform.TripResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/trips")
@Tag(name = "Trips", description = "Trip Management Endpoints")
public class TripController {
    private final TripCommandService tripCommandService;
    private final TripQueryService tripQueryService;

    public TripController(TripCommandService tripCommandService, TripQueryService tripQueryService) {
        this.tripCommandService = tripCommandService;
        this.tripQueryService = tripQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResource> getTripById(@PathVariable Long id) {
        var getTripByIdQuery = new GetAllTripsByIdQuery(id);
        var trip = tripQueryService.handle(getTripByIdQuery);
        if (trip.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(TripResourceFromEntityAssembler.toResourceFromEntity(trip.get()));
    }

    @GetMapping
    public ResponseEntity<List<TripResource>> getAllTrips() {
        var getAllTripsQuery = new GetAllTripsQuery();
        var trips = tripQueryService.handle(getAllTripsQuery);
        var tripsResource = trips.stream().map(TripResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(tripsResource);
    }

    @PostMapping
    public ResponseEntity<TripResource> createTrip(@RequestBody CreateTripResource resource) {
        var createTripCommand = CreateTripCommandFromResourceAssembler.toCommandFromResource(resource);
        var trip = tripCommandService.handle(createTripCommand);
        if (trip.isEmpty()) return ResponseEntity.notFound().build();
        var tripResource = TripResourceFromEntityAssembler.toResourceFromEntity(trip.get());
        return ResponseEntity.ok(tripResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResource> updateTrip(@PathVariable Long id, @RequestBody CreateTripResource resource) {
        var updateTripCommand = new UpdateTripCommand(
                id,
                resource.name(),
                resource.type(),
                resource.weight(),
                resource.unloadDirection(),
                resource.unloadLocation(),
                resource.unloadDate(),
                resource.expenseId(),
                resource.alertId(),
                resource.ongoingTripId(),
                resource.vehicleId(),
                resource.driverId(),
                resource.userId(),
                resource.destination(),
                resource.department(),
                resource.district(),
                resource.country(),
                resource.numberPackages(),
                resource.holderName(),
                resource.destinationDate(),
                resource.totalAmount(),
                resource.destinationAddress(),
                resource.loadDetail(),
                resource.pickupAddress()
        );
        var trip = tripCommandService.handle(updateTripCommand);
        if (trip.isEmpty()) return ResponseEntity.notFound().build();
        var tripResource = TripResourceFromEntityAssembler.toResourceFromEntity(trip.get());
        return ResponseEntity.ok(tripResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripCommandService.handle(new DeleteTripCommand(id));
        return ResponseEntity.noContent().build();
    }
}