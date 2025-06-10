package com.guia747.domain.exception;

public class InvalidGoogleTokenException extends RuntimeException {

    public InvalidGoogleTokenException() {
        super("Token inválido do Google.");
    }
}
