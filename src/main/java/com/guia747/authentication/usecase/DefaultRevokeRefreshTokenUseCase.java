package com.guia747.authentication.usecase;

import org.springframework.stereotype.Service;
import com.guia747.authentication.exception.InvalidRefreshTokenException;
import com.guia747.authentication.repository.RefreshTokenRepository;

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
