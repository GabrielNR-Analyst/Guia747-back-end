package com.guia747.authentication.usecase;

import org.springframework.stereotype.Service;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.accounts.domain.UserRepository;
import com.guia747.authentication.command.RefreshAccessTokenCommand;
import com.guia747.authentication.domain.RefreshTokenSession;
import com.guia747.authentication.dto.AuthenticationResponse;
import com.guia747.authentication.exception.InvalidRefreshTokenException;
import com.guia747.authentication.repository.RefreshTokenRepository;
import com.guia747.infrastructure.security.JwtTokenPair;
import com.guia747.infrastructure.security.JwtTokenService;

@Service
public class DefaultRefreshAccessTokenUseCase implements RefreshAccessTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public DefaultRefreshAccessTokenUseCase(RefreshTokenRepository refreshTokenRepository,
            JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticationResponse execute(RefreshAccessTokenCommand command) {
        RefreshTokenSession refreshToken = refreshTokenRepository.findByRefreshToken(command.refreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        UserAccount userAccount = userRepository.findById(refreshToken.getAccountId())
                .orElseThrow(InvalidRefreshTokenException::new);

        JwtTokenPair tokenPair = jwtTokenService.generateTokenPair(userAccount);
        RefreshTokenSession newRefreshToken = RefreshTokenSession.builder()
                .userAgent(command.userAgent())
                .ipAddress(command.ipAddress())
                .accountId(userAccount.getId())
                .tokenValue(tokenPair.refreshToken())
                .build();

        refreshTokenRepository.saveRefreshToken(newRefreshToken, tokenPair.refreshTokenTtl());
        refreshTokenRepository.deleteRefreshToken(refreshToken.getTokenValue());

        return new AuthenticationResponse(userAccount.getId(), tokenPair.accessToken(),
                tokenPair.accessTokenTtl().toSeconds(), tokenPair.refreshToken(),
                tokenPair.refreshTokenTtl().toSeconds(), "Bearer");
    }
}
