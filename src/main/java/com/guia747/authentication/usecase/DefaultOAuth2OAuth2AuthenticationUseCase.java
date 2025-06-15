package com.guia747.authentication.usecase;

import org.springframework.stereotype.Service;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.accounts.service.UserAccountService;
import com.guia747.authentication.dto.OAuth2AuthenticationRequest;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import com.guia747.infrastructure.oauth2.OAuth2TokenService;
import com.guia747.infrastructure.oauth2.OAuth2UserService;
import com.guia747.infrastructure.security.JwtTokenService;

@Service
public class DefaultOAuth2OAuth2AuthenticationUseCase implements OAuth2AuthenticationUseCase {

    private final OAuth2TokenService oauth2TokenService;
    private final OAuth2UserService oauth2UserService;
    private final UserAccountService userAccountService;
    private final JwtTokenService jwtTokenService;

    public DefaultOAuth2OAuth2AuthenticationUseCase(OAuth2TokenService tokenService,
            OAuth2UserService oAuth2UserService, UserAccountService userAccountService,
            JwtTokenService jwtTokenService) {
        this.oauth2TokenService = tokenService;
        this.oauth2UserService = oAuth2UserService;
        this.userAccountService = userAccountService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public UserAccount execute(OAuth2AuthenticationRequest request) {
        String accessToken = oauth2TokenService.exchangeCodeForAccessToken(request.code());
        OAuth2UserProfile oauth2UserProfile = oauth2UserService.getUserProfile(accessToken);

        UserAccount userAccount = userAccountService.findOrCreateFromOAuth2(oauth2UserProfile);
        jwtTokenService.generateAccessToken(userAccount);

        return userAccount;
    }
}
