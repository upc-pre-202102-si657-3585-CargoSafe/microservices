package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record CreateOnGoingTripCommand(Float latitude, Float longitude, Integer speed, Integer distance) {
}
