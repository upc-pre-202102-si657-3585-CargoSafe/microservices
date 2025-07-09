package com.dynoware.cargosafe.requestservice.request.domain.exceptions;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(String message) {
        super(message);
    }
} 