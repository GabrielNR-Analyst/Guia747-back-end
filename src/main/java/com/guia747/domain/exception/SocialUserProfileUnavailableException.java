package com.guia747.domain.exception;

public class SocialUserProfileUnavailableException extends SocialAuthenticationException {

    public SocialUserProfileUnavailableException(String provider) {
        super(String.format("Não foi possível recuperar o perfil do usuário do provedor %s.", provider.toLowerCase()));
    }
}
