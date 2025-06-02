package com.dynoware.cargosafe.companieservice.companies.interfaces.rest;

import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.DeleteCompanieCommand;
import com.dynoware.cargosafe.companieservice.companies.domain.model.queries.GetCompanieByIdQuery;
import com.dynoware.cargosafe.companieservice.companies.domain.services.CompanieCommandService;
import com.dynoware.cargosafe.companieservice.companies.domain.services.CompanieQueryService;
import com.dynoware.cargosafe.companieservice.companies.interfaces.rest.resources.CompanieResource;
import com.dynoware.cargosafe.companieservice.companies.interfaces.rest.resources.CreateCompanieResource;
import com.dynoware.cargosafe.companieservice.companies.interfaces.rest.transform.CompanieResourceFromEntityAssembler;
import com.dynoware.cargosafe.companieservice.companies.interfaces.rest.transform.CreateCompanieCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/companie", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Companie", description = "Available Companie Endpoints")
public class CompanieController {
    private final CompanieCommandService companieCommandService;
    private final CompanieQueryService companieQueryService;

    public CompanieController(CompanieCommandService companieCommandService, CompanieQueryService companieQueryService) {
        this.companieCommandService = companieCommandService;
        this.companieQueryService = companieQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a companie", description = "Create a companie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Companie created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public void createCompanie(@RequestBody CreateCompanieResource resource){
        var createCompanieCommand = CreateCompanieCommandFromResourceAssembler.toCommandFromResource(resource);
        companieCommandService.handle(createCompanieCommand);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get companie by id", description = "Get companie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Companie found"),
            @ApiResponse(responseCode = "404", description = "Companie not found")
    })
    public ResponseEntity<CompanieResource> getCompanieById(@PathVariable Long id) {
        var getCompanieByIdQuery = new GetCompanieByIdQuery(id);
        var companie = companieQueryService.handle(getCompanieByIdQuery);
        if (companie.isEmpty()) return ResponseEntity.notFound().build();
        var companieEntity = companie.get();
        var compenieResource = CompanieResourceFromEntityAssembler.toResourceFromEntity(companieEntity);
        return ResponseEntity.ok(compenieResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete companie by id", description = "Delete companie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Companie deleted"),
            @ApiResponse(responseCode = "404", description = "Companie not found")
    })
    public ResponseEntity<?> deleteCompanieById(@PathVariable Long id) {
        var deleteCompanieCommand = new DeleteCompanieCommand(id);
        companieCommandService.handle(deleteCompanieCommand);
        return ResponseEntity.ok("Companie with given id successfully deleted");
    }
}
