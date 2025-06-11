package com.guia747.application.usecase;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.guia747.domain.entity.RefreshTokenSession;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.exception.InvalidRefreshTokenException;
import com.guia747.domain.exception.UserAccountNotFoundException;
import com.guia747.domain.repository.RefreshTokenSessionRepository;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.domain.vo.TokenPair;
import com.guia747.infrastructure.service.JwtTokenService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshAccessTokenUseCaseImplTest {

    @Mock
    private RefreshTokenSessionRepository refreshTokenRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private RefreshAccessTokenUseCaseImpl useCase;

    private RefreshTokenSession session;

    private static final String TEST_REFRESH_TOKEN = "refreshToken";
    private static final String TEST_ACCESS_TOKEN = "accessToken";
    private static final UUID TEST_ACCOUNT_ID = UUID.randomUUID();
    private static final String TEST_EMAIL = "<EMAIL>";
    private static final String TEST_NAME = "Test User";
    private static final Duration TEST_ACCESS_TOKEN_TTL = Duration.ofDays(1);
    private static final Duration TEST_REFRESH_TOKEN_TTL = Duration.ofDays(30);

    @BeforeEach
    void setUp() {
        this.session = new RefreshTokenSession(
                TEST_REFRESH_TOKEN,
                TEST_ACCOUNT_ID,
                TEST_REFRESH_TOKEN_TTL
        );
    }

    @Test
    void shouldRefreshTokenSuccessfully() {
        UserAccount account = new UserAccount(TEST_EMAIL, TEST_NAME);
        account.setId(TEST_ACCOUNT_ID);

        var tokenPair = new TokenPair(
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                TEST_ACCESS_TOKEN_TTL,
                TEST_REFRESH_TOKEN_TTL
        );

        when(refreshTokenRepository.findByToken(TEST_REFRESH_TOKEN))
                .thenReturn(Optional.of(session));
        when(userAccountRepository.findById(TEST_ACCOUNT_ID))
                .thenReturn(Optional.of(account));
        when(jwtTokenService.generateTokenPair(TEST_ACCOUNT_ID))
                .thenReturn(tokenPair);

        TokenPair result = useCase.execute(TEST_REFRESH_TOKEN);

        assertThat(result.accessToken()).isEqualTo(TEST_ACCESS_TOKEN);
        assertThat(result.refreshToken()).isEqualTo(TEST_REFRESH_TOKEN);
        assertThat(result.accessTokenTtl()).isEqualTo(TEST_ACCESS_TOKEN_TTL);
        assertThat(result.refreshTokenTtl()).isEqualTo(TEST_REFRESH_TOKEN_TTL);

        verify(refreshTokenRepository).findByToken(TEST_REFRESH_TOKEN);
        verify(userAccountRepository).findById(TEST_ACCOUNT_ID);
        verify(jwtTokenService).generateTokenPair(TEST_ACCOUNT_ID);
        verify(refreshTokenRepository).save(any(RefreshTokenSession.class));
        verify(refreshTokenRepository).deleteByToken(TEST_REFRESH_TOKEN);
    }

    @Test
    void shouldThrowExceptionWhenRefreshTokenNotFound() {
        when(refreshTokenRepository.findByToken(TEST_REFRESH_TOKEN))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(TEST_REFRESH_TOKEN))
                .isInstanceOf(InvalidRefreshTokenException.class);

        verify(userAccountRepository, never()).findById(any());
        verify(jwtTokenService, never()).generateTokenPair(any());
        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserAccountNotFound() {
        when(refreshTokenRepository.findByToken(anyString()))
                .thenReturn(Optional.of(session));
        when(userAccountRepository.findById(TEST_ACCOUNT_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(TEST_REFRESH_TOKEN))
                .isInstanceOf(UserAccountNotFoundException.class);

        verify(userAccountRepository).findById(TEST_ACCOUNT_ID);
        verify(jwtTokenService, never()).generateTokenPair(any());
        verify(refreshTokenRepository, never()).save(any());
    }
}
