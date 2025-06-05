package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllOnGoingTripsQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetOnGoingTripByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.OnGoingTripCommandService;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.OnGoingTripQueryService;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateOnGoingTripResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.OnGoingTripResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.UpdateOnGoingTripResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.CreateOnGoingTripCommandFromResourceAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.OnGoingTripResourceFromEntityAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.UpdateOnGoingTripCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1/on-going-trip", produces = APPLICATION_JSON_VALUE)
@Tag(name = "On Going Trips", description = "On Going Trips Management Endpoints")

public class OnGoingTripsController {
    private final OnGoingTripCommandService onGoingTripCommandService;
    private final OnGoingTripQueryService onGoingTripQueryService;

    public OnGoingTripsController(OnGoingTripCommandService onGoingTripCommandService, OnGoingTripQueryService onGoingTripQueryService) {
        this.onGoingTripCommandService = onGoingTripCommandService;
        this.onGoingTripQueryService = onGoingTripQueryService;
    }

    @PostMapping
    public ResponseEntity<OnGoingTripResource> createOnGoingTrip(@RequestBody CreateOnGoingTripResource createOnGoingTripResource) {
        var createOnGoingTripCommand = CreateOnGoingTripCommandFromResourceAssembler.toCommandFromResource(createOnGoingTripResource);
        var onGoingTripId = onGoingTripCommandService.handle(createOnGoingTripCommand);
        if (onGoingTripId == 0L) return ResponseEntity.badRequest().build();

        var getOnGoingTripByIdQuery = new GetOnGoingTripByIdQuery(onGoingTripId);
        var onGoingTrip = onGoingTripQueryService.handle(getOnGoingTripByIdQuery);
        if (onGoingTrip.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var onGoingTripResource = OnGoingTripResourceFromEntityAssembler.toResourceFromEntity(onGoingTrip.get());
        return new ResponseEntity<>(onGoingTripResource, HttpStatus.CREATED);
    }

    @GetMapping("/{onGoingTripId}")
    public ResponseEntity<OnGoingTripResource> getOnGoingTrip(@PathVariable Long onGoingTripId) {
        var getOnGoingTripByIdQuery = new GetOnGoingTripByIdQuery(onGoingTripId);
        var onGoingTrip = onGoingTripQueryService.handle(getOnGoingTripByIdQuery);
        if (onGoingTrip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var onGoingTripResource = OnGoingTripResourceFromEntityAssembler.toResourceFromEntity(onGoingTrip.get());
        return ResponseEntity.ok(onGoingTripResource);
    }

    @GetMapping
    public ResponseEntity<List<OnGoingTripResource>> getAllOnGoingTrips() {
        var getAllOnGoingTripsQuery = new GetAllOnGoingTripsQuery();
        var onGoingTrips = onGoingTripQueryService.handle(getAllOnGoingTripsQuery);
        var onGoingTripsResources = onGoingTrips.stream().map(OnGoingTripResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(onGoingTripsResources);
    }

    @PutMapping("/{onGoingTripId}")
    public ResponseEntity<OnGoingTripResource> updateOnGoingTrip(@PathVariable Long onGoingTripId, @RequestBody UpdateOnGoingTripResource updateOnGoingTripResource) {
        var updateOnGoingTripCommand = UpdateOnGoingTripCommandFromResourceAssembler.toCommandFromResource(onGoingTripId, updateOnGoingTripResource);
        var updateOnGoingTrip = onGoingTripCommandService.handle(updateOnGoingTripCommand);
        if (updateOnGoingTrip.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var onGoingTripResource = OnGoingTripResourceFromEntityAssembler.toResourceFromEntity(updateOnGoingTrip.get());
        return ResponseEntity.ok(onGoingTripResource);
    }
}
