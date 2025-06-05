package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Alert;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAlertByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllAlertQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.AlertCommandService;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.AlertQueryService;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.AlertResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateAlertResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1/alert", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alert", description = "Operations related to alerts")
public class AlertController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @Operation(summary = "Creates an alert", description = "Creates an alert with the data provided in the request body.")
    @ApiResponse(responseCode = "201", description = "Alert created successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request.")

    @PostMapping
    public ResponseEntity<AlertResource> createAlert(@RequestBody CreateAlertResource resource) {
        Optional<Alert> alert = alertCommandService
                .handle(CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource));
        return alert.map(source -> new ResponseEntity<>(AlertResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get a alert by ID", description = "Gets a alert by the ID provided in the request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert found"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long id) {
        Optional<Alert> alert = alertQueryService
                .handle(new GetAlertByIdQuery(id));
        return alert.map(source -> new ResponseEntity<>(AlertResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Alerts", description = "Gets all alerts in the request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts found"),
            @ApiResponse(responseCode = "404", description = "Alerts not found")
    })
    @GetMapping
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        List<Alert> alert = alertQueryService
                .handle(new GetAllAlertQuery());
        return ResponseEntity.ok(alert.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }
}
