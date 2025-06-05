package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

public record UpdateDriverCommand(Long id, String name, String dni, String license,String contactNum, String urlPhoto) {
}
