package com.guia747.authentication.usecase;

import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.guia747.application.authentication.usecase.impl.DefaultAuthenticateWithOAuth2UseCase;
import com.guia747.domain.users.entity.User;
import com.guia747.application.users.UserService;
import com.guia747.application.authentication.command.AuthenticateWithOAuth2Command;
import com.guia747.domain.authentication.entity.RefreshTokenSession;
import com.guia747.web.dtos.authentication.AuthenticationResponse;
import com.guia747.domain.authentication.repository.RefreshTokenRepository;
import com.guia747.infrastructure.oauth2.OAuth2TokenService;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import com.guia747.infrastructure.oauth2.OAuth2UserService;
import com.guia747.infrastructure.security.JwtTokenPair;
import com.guia747.infrastructure.security.JwtTokenService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticateWithOAuth2UseCaseTest {

    @Mock
    private OAuth2TokenService oauth2TokenService;

    @Mock
    private OAuth2UserService oauth2UserService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private DefaultAuthenticateWithOAuth2UseCase useCase;

    private static final String TEST_OAUTH2_ACCESS_TOKEN = "oauth2-access-token";
    private static final String TEST_AUTHORIZATION_CODE = "auth-code-123";
    private static final String TEST_JWT_ACCESS_TOKEN = "jwt-access-token";
    private static final String TEST_JWT_REFRESH_TOKEN = "jwt-refresh-token";
    private static final String TEST_IP_ADDRESS = "192.168.1.1";
    private static final String TEST_USER_AGENT = "Mozilla/5.0 Test Browser";
    private static final String TEST_GOOGLE_USER_ID = "google-user-id-123";
    private static final String TEST_USER_NAME = "John Doe";
    private static final String TEST_USER_EMAIL = "john.doe@example.com";
    private static final String TEST_USER_PICTURE_URL = "https://example.com/profile.jpg";
    private static final String TOKEN_TYPE_BEARER = "Bearer";

    private AuthenticateWithOAuth2Command command;
    private OAuth2UserProfile oauth2UserProfile;
    private User user;
    private JwtTokenPair tokenPair;

    @BeforeEach
    void setUp() {
        // Setup test data
        UUID accountId = UUID.randomUUID();

        command = new AuthenticateWithOAuth2Command(
                TEST_AUTHORIZATION_CODE,
                TEST_IP_ADDRESS,
                TEST_USER_AGENT
        );

        oauth2UserProfile = OAuth2UserProfile.withGoogleProvider(TEST_GOOGLE_USER_ID)
                .name(TEST_USER_NAME)
                .email(TEST_USER_EMAIL)
                .pictureUrl(TEST_USER_PICTURE_URL)
                .build();

        user = mock(User.class);
        when(user.getId()).thenReturn(accountId);

        tokenPair = new JwtTokenPair(
                TEST_JWT_ACCESS_TOKEN,
                TEST_JWT_REFRESH_TOKEN,
                Duration.ofMinutes(15),
                Duration.ofDays(7)
        );
    }

    @Test
    void shouldSuccessfullyCompleteOAuth2AuthenticationFlow() {
        when(oauth2TokenService.exchangeCodeForAccessToken(command.authorizationCode()))
                .thenReturn(TEST_OAUTH2_ACCESS_TOKEN);
        when(oauth2UserService.getUserProfile(TEST_OAUTH2_ACCESS_TOKEN))
                .thenReturn(oauth2UserProfile);
        when(userService.findOrCreateFromOAuth2(oauth2UserProfile))
                .thenReturn(user);
        when(jwtTokenService.generateTokenPair(user))
                .thenReturn(tokenPair);

        AuthenticationResponse response = useCase.execute(command);

        assertThat(response).isNotNull();
        assertThat(response.accountId()).isEqualTo(user.getId());
        assertThat(response.accessToken()).isEqualTo(tokenPair.accessToken());
        assertThat(response.expiresIn()).isEqualTo(tokenPair.accessTokenTtl().toSeconds());
        assertThat(response.refreshToken()).isEqualTo(tokenPair.refreshToken());
        assertThat(response.refreshTokenExpiresIn()).isEqualTo(tokenPair.refreshTokenTtl().toSeconds());
        assertThat(response.tokenType()).isEqualTo(TOKEN_TYPE_BEARER);

        verify(refreshTokenRepository).saveRefreshToken(any(RefreshTokenSession.class), any());
    }
}
