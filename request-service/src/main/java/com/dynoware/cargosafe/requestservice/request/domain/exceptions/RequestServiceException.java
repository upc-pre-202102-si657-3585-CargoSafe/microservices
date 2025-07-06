package com.dynoware.cargosafe.requestservice.request.domain.exceptions;

public class RequestServiceException extends RuntimeException {
    public RequestServiceException(String message) {
        super(message);
    }
    public RequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
} 