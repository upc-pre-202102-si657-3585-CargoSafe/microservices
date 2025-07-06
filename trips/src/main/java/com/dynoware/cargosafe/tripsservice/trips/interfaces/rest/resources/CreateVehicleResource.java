package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateVehicleResource(
        @NotBlank(message = "Model is required") String model,
        @NotBlank(message = "Plate is required") String plate,
        @NotNull(message = "Max load is required") @Positive(message = "Max load must be positive") Float maxLoad,
        @NotNull(message = "Volume is required") @Positive(message = "Volume must be positive") Float volume,
        @NotBlank(message = "Photo URL is required") String photoUrl
) {
}
