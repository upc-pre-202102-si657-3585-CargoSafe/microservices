package com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest;


import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.commands.DeletePaymentCardCommand;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.model.queries.GetPaymentCardByIdQuery;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services.PaymentCardCommandService;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.domain.services.PaymentCardQueryService;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.resources.CreatePaymentCardResource;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.resources.PaymentCardResource;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.transform.CreatePaymentCardCommandFromResourceAssembler;
import com.dynoware.cargosafe.paymentcardsservice.paymentCards.interfaces.rest.transform.PaymentCardResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/payment-card", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Payment Card", description = "Available Payment Card Endpoints")
public class PaymentCardController {
    private final PaymentCardCommandService paymentCardCommandService;
    private final PaymentCardQueryService paymentCardQueryService;

    public PaymentCardController(PaymentCardCommandService paymentCardCommandService, PaymentCardQueryService paymentCardQueryService) {
        this.paymentCardCommandService = paymentCardCommandService;
        this.paymentCardQueryService = paymentCardQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a payment card", description = "Create a payment card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment Card created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public void createPaymentCard(@RequestBody CreatePaymentCardResource resource) {
        var createPaymentCardCommand = CreatePaymentCardCommandFromResourceAssembler.toCommandFromResource(resource);
        paymentCardCommandService.handle(createPaymentCardCommand);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment card by id", description = "Get payment card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment card found"),
            @ApiResponse(responseCode = "404", description = "Payment card not found")
    })
    public ResponseEntity<PaymentCardResource> getPaymentCardById(@PathVariable Long id) {
        var getPaymentCardByIdQuery = new GetPaymentCardByIdQuery(id);
        var paymentCard = paymentCardQueryService.handle(getPaymentCardByIdQuery);
        if (paymentCard.isEmpty()) return ResponseEntity.notFound().build();
        var paymentCardEntity = paymentCard.get();
        var paymentCardResource = PaymentCardResourceFromEntityAssembler.toResourceFromEntity(paymentCardEntity);
        return ResponseEntity.ok(paymentCardResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment card by id", description = "Delete payment card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment card deleted"),
            @ApiResponse(responseCode = "404", description = "Payment card not found")
    })
    public ResponseEntity<?> deletePaymentCardById(@PathVariable Long id) {
        var deletePaymentCardCommand = new DeletePaymentCardCommand(id);
        paymentCardCommandService.handle(deletePaymentCardCommand);
        return ResponseEntity.ok("Payment card with given id successfully deleted");
    }
}