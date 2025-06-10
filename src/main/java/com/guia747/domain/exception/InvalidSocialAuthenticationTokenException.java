package com.guia747.domain.exception;

public class InvalidSocialAuthenticationTokenException extends SocialAuthenticationException {

    public InvalidSocialAuthenticationTokenException(String provider) {
        super(String.format("Token de autenticação do provedor %s inválido.", provider));
    }
}
