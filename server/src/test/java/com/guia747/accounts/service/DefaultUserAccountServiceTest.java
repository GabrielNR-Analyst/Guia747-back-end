package com.guia747.accounts.service;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.accounts.domain.UserRepository;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserAccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserAccountService userAccountService;

    private static final String TEST_PROVIDER_ID = "123456789";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PICTURE = "https://example.com/picture.jpg";

    @Test
    void shouldReturnExistingUserWhenFoundBySocialProvider() {
        OAuth2UserProfile userProfile = OAuth2UserProfile.withGoogleProvider(TEST_PROVIDER_ID)
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .pictureUrl(TEST_PICTURE)
                .build();

        UserAccount existingUser = UserAccount.createFromOAuth2Profile(userProfile);

        when(userRepository.findBySocialProvider("google", TEST_PROVIDER_ID))
                .thenReturn(Optional.of(existingUser));

        UserAccount result = userAccountService.findOrCreateFromOAuth2(userProfile);

        assertThat(result).isEqualTo(existingUser);
        verify(userRepository).findBySocialProvider("google", TEST_PROVIDER_ID);
        verify(userRepository, Mockito.never()).save(any(UserAccount.class));
    }

    @Test
    void shouldCreateNewUserWhenNotFoundBySocialProvider() {
        OAuth2UserProfile userProfile = OAuth2UserProfile.withGoogleProvider(TEST_PROVIDER_ID)
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .pictureUrl(TEST_PICTURE)
                .build();

        UserAccount userAccount = UserAccount.createFromOAuth2Profile(userProfile);

        when(userRepository.findBySocialProvider("google", TEST_PROVIDER_ID))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(UserAccount.class))).thenReturn(userAccount);

        UserAccount result = userAccountService.findOrCreateFromOAuth2(userProfile);

        assertThat(result).isEqualTo(userAccount);
        verify(userRepository).findBySocialProvider("google", "123456789");
        verify(userRepository).save(any(UserAccount.class));
    }
}
