package com.guia747.infrastructure.oauth2;

public interface OAuth2TokenExchangeService {

    OAuth2TokenExchangeResponse exchangeAuthorizationCode(String providerName, String authorizationCode, String state,
            String codeVerifier);
}
