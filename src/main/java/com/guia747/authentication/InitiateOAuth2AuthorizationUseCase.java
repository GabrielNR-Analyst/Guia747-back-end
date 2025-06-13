package com.guia747.authentication;

import com.guia747.infrastructure.oauth2.OAuth2AuthorizationResult;

public interface InitiateOAuth2AuthorizationUseCase {

    OAuth2AuthorizationResult execute(String providerName);
}
