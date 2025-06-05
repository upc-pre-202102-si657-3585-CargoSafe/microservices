package com.dynoware.cargosafe.tripsservice.trips.domain.exceptions;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(Long aLong) {

        super("Driver with id " + aLong + "not found");
    }
}
