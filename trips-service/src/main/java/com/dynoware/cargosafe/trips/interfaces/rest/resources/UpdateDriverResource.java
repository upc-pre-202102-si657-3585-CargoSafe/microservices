package com.dynoware.cargosafe.trips.interfaces.rest.resources;

public record UpdateDriverResource(String name, String dni, String license, String contactNum, String photoUrl) {
}
