package com.dynoware.cargosafe.trips.domain.model.commands;

public record CreateDriverCommand(String name, String dni, String license, String contactNum, String urlPhoto) {
}
