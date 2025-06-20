package com.guia747.shared.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractApiException extends RuntimeException {

    protected AbstractApiException(String message) {
        super(message);
    }

    public abstract String getErrorCode();

    public abstract HttpStatus getHttpStatus();

    public String getMessage() {
        return super.getMessage();
    }
}
