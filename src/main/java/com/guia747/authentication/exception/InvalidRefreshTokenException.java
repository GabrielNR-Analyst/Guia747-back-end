package com.guia747.authentication.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("The provided refresh token is invalid or expired");
    }
}
