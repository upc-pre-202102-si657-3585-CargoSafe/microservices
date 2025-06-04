package com.dynoware.cargosafe.trips.interfaces.rest.resources;

import java.util.Date;

public record AlertResource(Long id, String title, String description, Date date) {
}
