package com.guia747.authentication.usecase;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.accounts.domain.UserRepository;
import com.guia747.authentication.command.RefreshAccessTokenCommand;
import com.guia747.authentication.domain.RefreshTokenSession;
import com.guia747.authentication.dto.AuthenticationResponse;
import com.guia747.authentication.exception.InvalidRefreshTokenException;
import com.guia747.authentication.repository.RefreshTokenRepository;
import com.guia747.infrastructure.security.JwtTokenPair;
import com.guia747.infrastructure.security.JwtTokenService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultRefreshAccessTokenUseCaseTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultRefreshAccessTokenUseCase useCase;

    @Captor
    private ArgumentCaptor<RefreshTokenSession> refreshTokenSessionCaptor;

    private RefreshAccessTokenCommand command;
    private RefreshTokenSession existingRefreshToken;
    private UserAccount userAccount;
    private JwtTokenPair newTokenPair;

    private static final String TEST_EXISTING_REFRESH_TOKEN = "refreshToken";
    private static final String TEST_IP_ADDRESS = "192.168.1.1";
    private static final String TEST_UA = "Mozilla/5.0 Test Browser";

    @BeforeEach
    void setUp() {
        UUID accountId = UUID.randomUUID();

        String newAccessToken = "new-access-token";
        String newRefreshToken = "new-refresh-token";

        command = new RefreshAccessTokenCommand(TEST_EXISTING_REFRESH_TOKEN, TEST_IP_ADDRESS, TEST_UA);
        existingRefreshToken = RefreshTokenSession.builder()
                .accountId(accountId)
                .tokenValue(TEST_EXISTING_REFRESH_TOKEN)
                .userAgent("Old Browser")
                .build();

        userAccount = mock(UserAccount.class);
        lenient().when(userAccount.getId()).thenReturn(accountId);

        newTokenPair = new JwtTokenPair(
                newAccessToken,
                newRefreshToken,
                Duration.ofMinutes(15),
                Duration.ofDays(7)
        );
    }

    @Test
    void shouldSuccessfullyRefreshAccessToken() {
        when(refreshTokenRepository.findByRefreshToken(command.refreshToken()))
                .thenReturn(Optional.of(existingRefreshToken));
        when(userRepository.findById(existingRefreshToken.getAccountId()))
                .thenReturn(Optional.of(userAccount));
        when(jwtTokenService.generateTokenPair(userAccount))
                .thenReturn(newTokenPair);

        AuthenticationResponse response = useCase.execute(command);

        assertThat(response).isNotNull();
        assertThat(response.accountId()).isEqualTo(userAccount.getId());
        assertThat(response.accessToken()).isEqualTo(newTokenPair.accessToken());
        assertThat(response.expiresIn()).isEqualTo(newTokenPair.accessTokenTtl().toSeconds());
        assertThat(response.refreshToken()).isEqualTo(newTokenPair.refreshToken());
        assertThat(response.refreshTokenExpiresIn()).isEqualTo(newTokenPair.refreshTokenTtl().toSeconds());
        assertThat(response.tokenType()).isEqualTo("Bearer");

        verify(refreshTokenRepository).findByRefreshToken(command.refreshToken());
        verify(userRepository).findById(existingRefreshToken.getAccountId());
        verify(jwtTokenService).generateTokenPair(userAccount);
        verify(refreshTokenRepository).saveRefreshToken(any(RefreshTokenSession.class),
                eq(newTokenPair.refreshTokenTtl()));
        verify(refreshTokenRepository).deleteRefreshToken(existingRefreshToken.getTokenValue());
    }

    @Test
    void shouldSaveNewRefreshTokenSessionWithCorrectData() {
        when(refreshTokenRepository.findByRefreshToken(command.refreshToken()))
                .thenReturn(Optional.of(existingRefreshToken));
        when(userRepository.findById(existingRefreshToken.getAccountId()))
                .thenReturn(Optional.of(userAccount));
        when(jwtTokenService.generateTokenPair(userAccount))
                .thenReturn(newTokenPair);

        useCase.execute(command);

        verify(refreshTokenRepository).saveRefreshToken(refreshTokenSessionCaptor.capture(),
                eq(newTokenPair.refreshTokenTtl()));

        RefreshTokenSession capturedSession = refreshTokenSessionCaptor.getValue();
        assertThat(capturedSession.getAccountId()).isEqualTo(userAccount.getId());
        assertThat(capturedSession.getTokenValue()).isEqualTo(newTokenPair.refreshToken());
        assertThat(capturedSession.getIpAddress()).isEqualTo(command.ipAddress());
        assertThat(capturedSession.getUserAgent()).isEqualTo(command.userAgent());
    }

    @Test
    void shouldThrowExceptionWhenUserAccountNotFound() {
        when(refreshTokenRepository.findByRefreshToken(command.refreshToken()))
                .thenReturn(Optional.of(existingRefreshToken));
        when(userRepository.findById(existingRefreshToken.getAccountId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(command))
                .isInstanceOf(InvalidRefreshTokenException.class);

        verify(refreshTokenRepository).findByRefreshToken(command.refreshToken());
        verify(userRepository).findById(existingRefreshToken.getAccountId());
        verifyNoInteractions(jwtTokenService);
        verify(refreshTokenRepository, never()).saveRefreshToken(any(), any());
        verify(refreshTokenRepository, never()).deleteRefreshToken(any());
    }
}
