package com.dynoware.cargosafe.tripsservice.trips.domain.model.commands;

import java.util.Date;

public record CreateAlertCommand(String title, String description, Date date) {
    public CreateAlertCommand {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title cannot be null or empty");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description cannot be null or empty");
    }
}
