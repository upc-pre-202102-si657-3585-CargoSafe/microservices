package com.dynoware.cargosafe.requestservice.request.domain.exceptions;

public class RequestServiceNotFoundException extends RuntimeException {
    public RequestServiceNotFoundException(String message) {
        super(message);
    }
} 