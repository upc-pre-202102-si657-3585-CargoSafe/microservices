package com.dynoware.cargosafe.tripsservice.trips.domain.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException {
    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
} 