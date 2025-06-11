package com.guia747.application.usecase;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.guia747.application.dto.SocialAuthenticationResult;
import com.guia747.domain.entity.RefreshTokenSession;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.exception.SocialProviderAlreadyLinkedException;
import com.guia747.domain.repository.RefreshTokenSessionRepository;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.domain.service.SocialAuthenticationProvider;
import com.guia747.domain.vo.SocialAuthenticationToken;
import com.guia747.domain.vo.SocialUserProfile;
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
class AuthenticateWithSocialProviderUseCaseImplTest {

    @Mock
    private SocialAuthenticationProvider socialAuthenticationProvider;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private RefreshTokenSessionRepository refreshTokenRepository;

    @InjectMocks
    private AuthenticateWithSocialProviderUseCaseImpl useCase;

    private SocialUserProfile socialUserProfile;
    private TokenPair tokenPair;

    private static final String TEST_PROVIDER_ID = "googleid";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_PICTURE_URL = "https://example.com/pic.png";
    private static final String TEST_ACCESS_TOKEN = "access";
    private static final String TEST_REFRESH_TOKEN = "refresh";
    private static final Duration TEST_ACCESS_TOKEN_TTL = Duration.ofHours(1);
    private static final Duration TEST_REFRESH_TOKEN_TTL = Duration.ofDays(30);

    @BeforeEach
    void setUp() {
        this.socialUserProfile = new SocialUserProfile(TEST_PROVIDER_ID, TEST_EMAIL, TEST_NAME,
                TEST_PICTURE_URL);
        this.tokenPair = new TokenPair(TEST_ACCESS_TOKEN, TEST_REFRESH_TOKEN, TEST_ACCESS_TOKEN_TTL,
                TEST_REFRESH_TOKEN_TTL);
    }

    @Test
    void shouldAuthenticateExistingUserFoundByProviderId() {
        var existingUser = UserAccount.createFromSocialProfile(socialUserProfile);
        existingUser.setId(UUID.randomUUID());

        when(socialAuthenticationProvider.validateTokenAndExtractProfile(any(SocialAuthenticationToken.class)))
                .thenReturn(socialUserProfile);
        when(userAccountRepository.findByGoogleId(socialUserProfile.providerId()))
                .thenReturn(Optional.of(existingUser));
        when(jwtTokenService.generateTokenPair(existingUser.getId()))
                .thenReturn(tokenPair);

        SocialAuthenticationResult result = useCase.execute(socialUserProfile.providerId());

        assertThat(result.userId()).isEqualTo(existingUser.getId());
        assertThat(result.tokenPair()).isEqualTo(tokenPair);
        assertThat(result.isNewAccount()).isFalse();

        verify(socialAuthenticationProvider).validateTokenAndExtractProfile(any(SocialAuthenticationToken.class));
        verify(userAccountRepository).findByGoogleId(TEST_PROVIDER_ID);
        verify(jwtTokenService).generateTokenPair(existingUser.getId());
        verify(refreshTokenRepository).save(any(RefreshTokenSession.class));

        // Should NOT find by email since found by Google ID
        verify(userAccountRepository, never()).findByEmail(any(String.class));
    }

    @Test
    void shouldReconnectExistingUserFoundByEmail() {
        // Given - User exists by email but not by Google ID (revocation scenario)
        var existingUser = UserAccount.createFromSocialProfile(socialUserProfile);
        existingUser.setId(UUID.randomUUID());

        when(socialAuthenticationProvider.validateTokenAndExtractProfile(any(SocialAuthenticationToken.class)))
                .thenReturn(socialUserProfile);
        when(userAccountRepository.findByGoogleId(TEST_PROVIDER_ID))
                .thenReturn(Optional.empty()); // Not found by google id
        when(userAccountRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(existingUser)); // Found by email
        when(jwtTokenService.generateTokenPair(existingUser.getId()))
                .thenReturn(tokenPair);

        SocialAuthenticationResult result = useCase.execute(TEST_PROVIDER_ID);

        assertThat(result.userId()).isEqualTo(existingUser.getId());
        assertThat(result.tokenPair()).isEqualTo(tokenPair);
        assertThat(result.isNewAccount()).isFalse(); // Should be existing account

        // Verify reconnection process
        ArgumentCaptor<UserAccount> userCaptor = ArgumentCaptor.forClass(UserAccount.class);
        verify(userAccountRepository).save(userCaptor.capture());

        UserAccount savedUser = userCaptor.getValue();
        assertThat(savedUser.getId()).isEqualTo(existingUser.getId());

        verify(refreshTokenRepository).save(any(RefreshTokenSession.class));
    }

    @Test
    void shouldCreateNewUserWhenNoExistingAccountFound() {
        var newUser = UserAccount.createFromSocialProfile(socialUserProfile);
        newUser.setId(UUID.randomUUID());

        when(socialAuthenticationProvider.validateTokenAndExtractProfile(any(SocialAuthenticationToken.class)))
                .thenReturn(socialUserProfile);
        when(userAccountRepository.findByGoogleId(anyString()))
                .thenReturn(Optional.empty());
        when(userAccountRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());
        when(userAccountRepository.save(any(UserAccount.class)))
                .thenReturn(newUser);
        when(jwtTokenService.generateTokenPair(any()))
                .thenReturn(tokenPair);

        SocialAuthenticationResult result = useCase.execute(TEST_PROVIDER_ID);

        assertThat(result.userId()).isEqualTo(newUser.getId());
        assertThat(result.tokenPair()).isEqualTo(tokenPair);
        assertThat(result.isNewAccount()).isTrue(); // Should be new account

        // Verify new user account
        ArgumentCaptor<UserAccount> userCaptor = ArgumentCaptor.forClass(UserAccount.class);
        verify(userAccountRepository).save(userCaptor.capture());

        UserAccount savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(savedUser.getName()).isEqualTo(TEST_NAME);

        verify(refreshTokenRepository).save(any(RefreshTokenSession.class));
    }

    @Test
    void shouldThrowExceptionWhenGoogleIdLinkedToDifferentUser() {
        var existingUserId = UUID.randomUUID();
        var conflictingUserId = UUID.randomUUID();

        var existingByEmail = new UserAccount(TEST_EMAIL, TEST_NAME);
        existingByEmail.setId(existingUserId);

        var conflictingUser = UserAccount.createFromSocialProfile(socialUserProfile);
        conflictingUser.setId(conflictingUserId);

        when(socialAuthenticationProvider.validateTokenAndExtractProfile(any(SocialAuthenticationToken.class)))
                .thenReturn(socialUserProfile);
        when(userAccountRepository.findByGoogleId(TEST_PROVIDER_ID))
                .thenReturn(Optional.empty()) // Not found in primary search
                .thenReturn(Optional.of(conflictingUser)); // Found in conflict check
        when(userAccountRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(existingByEmail));

        assertThatThrownBy(() -> useCase.execute(TEST_PROVIDER_ID))
                .isInstanceOf(SocialProviderAlreadyLinkedException.class);

        verify(userAccountRepository, never()).save(any(UserAccount.class));
        verify(jwtTokenService, never()).generateTokenPair(any());
        verify(refreshTokenRepository, never()).save(any(RefreshTokenSession.class));
    }
}
