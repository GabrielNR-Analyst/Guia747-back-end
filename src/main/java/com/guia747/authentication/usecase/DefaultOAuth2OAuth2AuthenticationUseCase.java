package com.guia747.authentication.usecase;

import org.springframework.stereotype.Service;
import com.guia747.authentication.OAuth2AuthenticationRequest;
import com.guia747.authentication.OAuth2UserProfile;
import com.guia747.infrastructure.oauth2.OAuth2TokenService;
import com.guia747.infrastructure.oauth2.OAuth2UserService;

@Service
public class DefaultOAuth2OAuth2AuthenticationUseCase implements OAuth2AuthenticationUseCase {

    private final OAuth2TokenService oauth2TokenService;
    private final OAuth2UserService oauth2UserService;

    public DefaultOAuth2OAuth2AuthenticationUseCase(OAuth2TokenService tokenService,
            OAuth2UserService oAuth2UserService) {
        this.oauth2TokenService = tokenService;
        this.oauth2UserService = oAuth2UserService;
    }

    @Override
    public void execute(OAuth2AuthenticationRequest request) {
        String accessToken = oauth2TokenService.exchangeCodeForAccessToken(request.code());
        OAuth2UserProfile userProfile = oauth2UserService.getUserProfile(accessToken);
    }
}
