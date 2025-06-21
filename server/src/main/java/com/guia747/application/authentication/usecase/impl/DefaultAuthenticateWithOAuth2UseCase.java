package com.guia747.application.authentication.usecase.impl;

import org.springframework.stereotype.Service;
import com.guia747.application.authentication.command.AuthenticateWithOAuth2Command;
import com.guia747.application.authentication.usecase.AuthenticateWithOAuth2UseCase;
import com.guia747.domain.users.entity.User;
import com.guia747.application.users.UserService;
import com.guia747.domain.authentication.entity.RefreshTokenSession;
import com.guia747.web.dtos.authentication.AuthenticationResponse;
import com.guia747.domain.authentication.repository.RefreshTokenRepository;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import com.guia747.infrastructure.oauth2.OAuth2TokenService;
import com.guia747.infrastructure.oauth2.OAuth2UserService;
import com.guia747.infrastructure.security.JwtTokenPair;
import com.guia747.infrastructure.security.JwtTokenService;

@Service
public class DefaultAuthenticateWithOAuth2UseCase implements AuthenticateWithOAuth2UseCase {

    private final OAuth2TokenService oauth2TokenService;
    private final OAuth2UserService oauth2UserService;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public DefaultAuthenticateWithOAuth2UseCase(
            OAuth2TokenService tokenService,
            OAuth2UserService oAuth2UserService,
            UserService userService,
            JwtTokenService jwtTokenService,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.oauth2TokenService = tokenService;
        this.oauth2UserService = oAuth2UserService;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public AuthenticationResponse execute(AuthenticateWithOAuth2Command command) {
        String oauth2AccessToken = oauth2TokenService.exchangeCodeForAccessToken(command.authorizationCode());
        OAuth2UserProfile oauth2UserProfile = oauth2UserService.getUserProfile(oauth2AccessToken);

        User user = userService.findOrCreateFromOAuth2(oauth2UserProfile);

        JwtTokenPair tokenPair = jwtTokenService.generateTokenPair(user);

        RefreshTokenSession session = RefreshTokenSession.builder()
                .accountId(user.getId())
                .tokenValue(tokenPair.refreshToken())
                .ipAddress(command.ipAddress())
                .userAgent(command.userAgent())
                .build();

        refreshTokenRepository.saveRefreshToken(session, tokenPair.refreshTokenTtl());

        return new AuthenticationResponse(user.getId(), tokenPair.accessToken(),
                tokenPair.accessTokenTtl().toSeconds(), tokenPair.refreshToken(),
                tokenPair.refreshTokenTtl().toSeconds(), "Bearer");
    }
}
