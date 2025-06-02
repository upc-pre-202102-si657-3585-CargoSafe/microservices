package com.dynoware.cargosafe.trips.domain.model.commands;

public record CreateOnGoingTripCommand(Float latitude, Float longitude, Integer speed, Integer distance) {
}
