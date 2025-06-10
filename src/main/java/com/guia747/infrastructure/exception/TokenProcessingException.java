package com.guia747.infrastructure.exception;

public class TokenProcessingException extends RuntimeException {

    public TokenProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
