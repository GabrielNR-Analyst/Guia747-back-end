package com.guia747.application.authentication.usecase.impl;

import org.springframework.stereotype.Service;
import com.guia747.application.authentication.usecase.RefreshAccessTokenUseCase;
import com.guia747.domain.users.entity.User;
import com.guia747.domain.users.repository.UserRepository;
import com.guia747.application.authentication.command.RefreshAccessTokenCommand;
import com.guia747.domain.authentication.entity.RefreshTokenSession;
import com.guia747.web.dtos.authentication.AuthenticationResponse;
import com.guia747.domain.authentication.exception.InvalidRefreshTokenException;
import com.guia747.domain.authentication.repository.RefreshTokenRepository;
import com.guia747.infrastructure.security.JwtTokenPair;
import com.guia747.infrastructure.security.JwtTokenService;

@Service
public class DefaultRefreshAccessTokenUseCase implements RefreshAccessTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public DefaultRefreshAccessTokenUseCase(
            RefreshTokenRepository refreshTokenRepository,
            JwtTokenService jwtTokenService,
            UserRepository userRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticationResponse execute(RefreshAccessTokenCommand command) {
        RefreshTokenSession refreshToken = refreshTokenRepository.findByRefreshToken(command.refreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        User user = userRepository.findById(refreshToken.getAccountId())
                .orElseThrow(InvalidRefreshTokenException::new);

        JwtTokenPair tokenPair = jwtTokenService.generateTokenPair(user);
        RefreshTokenSession newRefreshToken = RefreshTokenSession.builder()
                .userAgent(command.userAgent())
                .ipAddress(command.ipAddress())
                .accountId(user.getId())
                .tokenValue(tokenPair.refreshToken())
                .build();

        refreshTokenRepository.saveRefreshToken(newRefreshToken, tokenPair.refreshTokenTtl());
        refreshTokenRepository.deleteRefreshToken(refreshToken.getTokenValue());

        return new AuthenticationResponse(user.getId(), tokenPair.accessToken(),
                tokenPair.accessTokenTtl().toSeconds(), tokenPair.refreshToken(),
                tokenPair.refreshTokenTtl().toSeconds(), "Bearer");
    }
}
