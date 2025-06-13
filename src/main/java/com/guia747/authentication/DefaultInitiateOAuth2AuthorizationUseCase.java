package com.guia747.authentication;

import org.springframework.stereotype.Service;
import com.guia747.infrastructure.oauth2.OAuth2AuthorizationResult;
import com.guia747.infrastructure.oauth2.OAuth2AuthorizationService;

@Service
public class DefaultInitiateOAuth2AuthorizationUseCase implements InitiateOAuth2AuthorizationUseCase {

    private final OAuth2AuthorizationService oauth2AuthorizationService;

    public DefaultInitiateOAuth2AuthorizationUseCase(OAuth2AuthorizationService oauth2AuthorizationService) {
        this.oauth2AuthorizationService = oauth2AuthorizationService;
    }

    @Override
    public OAuth2AuthorizationResult execute(String providerName) {
        return oauth2AuthorizationService.generateAuthorizationUrl(providerName);
    }
}
