package com.guia747.infrastructure.oauth2;

import com.guia747.authentication.OAuth2UserInfo;

public interface OAuth2TokenExchangeService {

    OAuth2TokenExchangeResponse exchangeAuthorizationCode(String providerName, String authorizationCode, String state,
            String codeVerifier);

    OAuth2UserInfo fetchUserInfo(String providerName, String accessToken);
}
