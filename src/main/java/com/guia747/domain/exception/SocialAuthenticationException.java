package com.guia747.domain.exception;

public abstract class SocialAuthenticationException extends RuntimeException {

    public SocialAuthenticationException(String message) {
        super(message);
    }
}
