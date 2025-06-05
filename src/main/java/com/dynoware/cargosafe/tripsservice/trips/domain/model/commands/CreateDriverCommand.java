package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record CreateDriverCommand(String name, String dni, String license, String contactNum, String urlPhoto) {
}
