package com.guia747.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.entity.RefreshTokenSession;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.exception.InvalidRefreshTokenException;
import com.guia747.domain.exception.UserAccountNotFoundException;
import com.guia747.domain.repository.RefreshTokenSessionRepository;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.domain.vo.TokenPair;
import com.guia747.infrastructure.service.JwtTokenService;

@Service
public class RefreshAccessTokenUseCaseImpl implements RefreshAccessTokenUseCase {

    private final RefreshTokenSessionRepository refreshTokenRepository;
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenService jwtTokenService;

    public RefreshAccessTokenUseCaseImpl(
            RefreshTokenSessionRepository refreshTokenRepository,
            UserAccountRepository userAccountRepository,
            JwtTokenService jwtTokenService
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    @Transactional
    public TokenPair execute(String refreshToken) {
        RefreshTokenSession session = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);

        UserAccount userAccount = userAccountRepository.findById(session.getAccountId())
                .orElseThrow(UserAccountNotFoundException::new);

        TokenPair tokenPair = jwtTokenService.generateTokenPair(userAccount.getId());

        RefreshTokenSession newSession = new RefreshTokenSession(
                tokenPair.refreshToken(),
                userAccount.getId(),
                tokenPair.refreshTokenTtl()
        );
        refreshTokenRepository.save(newSession);
        refreshTokenRepository.deleteByToken(refreshToken);

        return tokenPair;
    }
}
