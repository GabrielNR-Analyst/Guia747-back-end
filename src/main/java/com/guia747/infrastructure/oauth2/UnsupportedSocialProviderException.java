package com.guia747.infrastructure.oauth2;

public class UnsupportedSocialProviderException extends RuntimeException {

    public UnsupportedSocialProviderException(String message) {
        super(message);
    }
}
