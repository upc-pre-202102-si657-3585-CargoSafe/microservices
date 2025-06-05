package com.dynoware.cargosafe.tripsservice.trips.domain.exceptions;

public class OnGoingTripNotFoundException extends RuntimeException {
    public OnGoingTripNotFoundException(Long aLong) {

        super("On going trip id" + aLong + "not found");
    }
}
