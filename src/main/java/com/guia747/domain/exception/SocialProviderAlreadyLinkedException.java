package com.guia747.domain.exception;

public class SocialProviderAlreadyLinkedException extends SocialAuthenticationException {

    public SocialProviderAlreadyLinkedException(String provider) {
        super(String.format("A conta %s que você tentou usar já está conectada a outro usuário.", provider));
    }
}
