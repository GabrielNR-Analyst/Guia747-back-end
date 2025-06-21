package com.guia747.application.authentication.usecase.impl;

import org.springframework.stereotype.Service;
import com.guia747.application.authentication.usecase.RevokeRefreshTokenUseCase;
import com.guia747.domain.authentication.exception.InvalidRefreshTokenException;
import com.guia747.domain.authentication.repository.RefreshTokenRepository;

@Service
public class DefaultRevokeRefreshTokenUseCase implements RevokeRefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    public DefaultRevokeRefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void execute(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(InvalidRefreshTokenException::new);
        refreshTokenRepository.deleteRefreshToken(refreshToken);
    }
}
