package com.guia747.infrastructure.exception;

public class OAuth2AuthenticationException extends RuntimeException {

    public OAuth2AuthenticationException(String message) {
        super(message);
    }
}
