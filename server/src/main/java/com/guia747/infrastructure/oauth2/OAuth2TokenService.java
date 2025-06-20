package com.guia747.infrastructure.oauth2;

public interface OAuth2TokenService {

    String exchangeCodeForAccessToken(String authorizationCode);
}
