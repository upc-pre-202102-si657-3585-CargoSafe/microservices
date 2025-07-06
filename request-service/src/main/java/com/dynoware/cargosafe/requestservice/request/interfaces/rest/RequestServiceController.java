package com.dynoware.cargosafe.requestservice.request.interfaces.rest;

import com.dynoware.cargosafe.requestservice.request.application.internal.commandservices.RequestServiceCommandServiceImpl;
import com.dynoware.cargosafe.requestservice.request.application.internal.queryservices.RequestServiceQueryServiceImpl;
import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.DeleteRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.UpdateRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.queries.GetAllRequestServiceQuery;
import com.dynoware.cargosafe.requestservice.request.domain.model.queries.GetRequestServiceByIdQuery;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources.CreateRequestServiceResource;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources.RequestServiceResource;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.resources.UpdateRequestServiceStatusResource;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.transform.CreateRequestServiceCommandFromResourceAssembler;
import com.dynoware.cargosafe.requestservice.request.interfaces.rest.transform.RequestServiceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Request Service", description = "Trip Request service Endpoints")
@RequestMapping("/api/v1/requestServices")
public class RequestServiceController {
    private final RequestServiceCommandServiceImpl commandService;
    private final RequestServiceQueryServiceImpl queryService;

    public RequestServiceController(RequestServiceCommandServiceImpl commandService, RequestServiceQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<RequestServiceResource> createRequestService(@RequestBody CreateRequestServiceResource resource) {
        var command = CreateRequestServiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var createdRequestService = commandService.handle(command);
        var requestServiceResource = RequestServiceResourceFromEntityAssembler.transformResourceFromEntity(createdRequestService);
        return ResponseEntity.ok(requestServiceResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestServiceResource> updateRequestService(@PathVariable Long id, @RequestBody CreateRequestServiceResource resource) {
        var command = new UpdateRequestServiceCommand(id,
                resource.unloadDirection(),
                resource.type(),
                resource.numberPackages(),
                resource.country(),
                resource.department(),
                resource.district(),
                resource.destination(),
                resource.unloadLocation(),
                resource.unloadDate(),
                resource.distance(),
                resource.statusId(),
                resource.holderName(),
                resource.pickupAddress(),
                resource.pickupLat(),
                resource.pickupLng(),
                resource.destinationAddress(),
                resource.destinationLat(),
                resource.destinationLng(),
                resource.loadDetail(),
                resource.weight()
        );
        var updatedRequestService = commandService.handle(command);
        var requestServiceResource = RequestServiceResourceFromEntityAssembler.transformResourceFromEntity(updatedRequestService);
        return ResponseEntity.ok(requestServiceResource);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RequestServiceResource> updateRequestServiceStatus(@PathVariable Long id, @RequestBody UpdateRequestServiceStatusResource resource) {
        var requestService = queryService.handle(new GetRequestServiceByIdQuery(id))
                .orElseThrow(() -> new IllegalArgumentException("RequestService not found"));

        var status = commandService.updateStatus(requestService, resource.statusId());
        var requestServiceResource = RequestServiceResourceFromEntityAssembler.transformResourceFromEntity(status);
        return ResponseEntity.ok(requestServiceResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestServiceResource> getRequestServiceById(@PathVariable Long id) {
        var query = new GetRequestServiceByIdQuery(id);
        Optional<RequestService> requestService = queryService.handle(query);
        if (requestService.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var requestServiceResource = RequestServiceResourceFromEntityAssembler.transformResourceFromEntity(requestService.get());
        return ResponseEntity.ok(requestServiceResource);
    }

    @GetMapping
    public ResponseEntity<List<RequestServiceResource>> getAllRequestServices() {
        var query = new GetAllRequestServiceQuery();
        List<RequestService> requestServices = queryService.handle(query);
        var requestServiceResources = requestServices.stream()
                .map(RequestServiceResourceFromEntityAssembler::transformResourceFromEntity)
                .toList();
        return ResponseEntity.ok(requestServiceResources);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RequestServiceResource>> getRequestServicesByUserId(@PathVariable Long userId) {
        List<RequestService> requestServices = queryService.handleByUserId(userId);
        var resources = requestServices.stream()
            .map(RequestServiceResourceFromEntityAssembler::transformResourceFromEntity)
            .toList();
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestService(@PathVariable Long id) {
        var command = new DeleteRequestServiceCommand(id);
        commandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}