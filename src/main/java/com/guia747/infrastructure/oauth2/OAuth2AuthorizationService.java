package com.guia747.infrastructure.oauth2;

public interface OAuth2AuthorizationService {

    OAuth2AuthorizationResult generateAuthorizationUrl(String providerName);
}
