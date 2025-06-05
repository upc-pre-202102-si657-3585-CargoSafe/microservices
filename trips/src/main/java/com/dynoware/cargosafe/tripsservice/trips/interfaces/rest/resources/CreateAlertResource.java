package com.dynoware.cargosafe.tripsservice.trips.interfaces.rest.resources;

import java.util.Date;

public record CreateAlertResource(String title, String description, Date date) {
    public CreateAlertResource {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }
}