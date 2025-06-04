package com.dynoware.cargosafe.trips.domain.exceptions;

public class OnGoingTripNotFoundException extends RuntimeException {
    public OnGoingTripNotFoundException(Long aLong) {

        super("On going trip id" + aLong + "not found");
    }
}
