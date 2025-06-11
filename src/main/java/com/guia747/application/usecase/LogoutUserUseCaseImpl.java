package com.guia747.application.usecase;

import org.springframework.stereotype.Service;
import com.guia747.domain.repository.RefreshTokenSessionRepository;

@Service
public class LogoutUserUseCaseImpl implements LogoutUserUseCase {

    private final RefreshTokenSessionRepository refreshTokenRepository;

    public LogoutUserUseCaseImpl(RefreshTokenSessionRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void execute(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
