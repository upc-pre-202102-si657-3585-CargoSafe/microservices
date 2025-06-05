package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest;

import com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates.Expense;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetAllExpenseQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.queries.GetExpenseByIdQuery;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.ExpenseCommandService;
import com.dynoware.cargosafe.tripsservice.trips.domain.services.ExpenseQueryService;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.CreateExpenseResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources.ExpenseResource;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.CreateExpenseCommandFromResourceAssembler;
import com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.transform.ExpenseResourceFromEntityAssembler;
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
@RequestMapping(value = "api/v1/expense", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Expense", description = "Operations related to expenses")
public class ExpenseController {

    private final ExpenseCommandService expenseCommandService;
    private final ExpenseQueryService expenseQueryService;

    public ExpenseController(ExpenseCommandService expenseCommandService, ExpenseQueryService expenseQueryService) {
        this.expenseCommandService = expenseCommandService;
        this.expenseQueryService = expenseQueryService;
    }

    @Operation(summary = "Creates an expense", description = "Creates an expense with the data provided in the request body.")
    @ApiResponse(responseCode = "201", description = "Expense created successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request.")

    @PostMapping
    public ResponseEntity<ExpenseResource> createExpenses(@RequestBody CreateExpenseResource resource) {
        Optional<Expense> expenses = expenseCommandService
                .handle(CreateExpenseCommandFromResourceAssembler.toCommandFromResource(resource));
        return expenses.map(source -> new ResponseEntity<>(ExpenseResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get a expense by ID", description = "Gets an expense by the ID provided in the request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense found"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResource> getExpensesById(@PathVariable Long id) {
        Optional<Expense> expenses = expenseQueryService
                .handle(new GetExpenseByIdQuery(id));
        return expenses.map(source -> new ResponseEntity<>(ExpenseResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get expenses", description = "Gets all expense in the request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses found"),
            @ApiResponse(responseCode = "404", description = "Expenses not found")
    })
    @GetMapping
    public ResponseEntity<List<ExpenseResource>> getAllExpenses() {
        List<Expense> expenses = expenseQueryService
                .handle(new GetAllExpenseQuery());
        return ResponseEntity.ok(expenses.stream()
                .map(ExpenseResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList()));
    }
}
