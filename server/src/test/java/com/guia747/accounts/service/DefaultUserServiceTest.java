package com.guia747.accounts.service;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.guia747.application.users.DefaultUserService;
import com.guia747.domain.users.entity.User;
import com.guia747.domain.users.repository.UserRepository;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userAccountService;

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

        User existingUser = User.createFromOAuth2Profile(userProfile);

        when(userRepository.findBySocialProvider("google", TEST_PROVIDER_ID))
                .thenReturn(Optional.of(existingUser));

        User result = userAccountService.findOrCreateFromOAuth2(userProfile);

        assertThat(result).isEqualTo(existingUser);
        verify(userRepository).findBySocialProvider("google", TEST_PROVIDER_ID);
        verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    void shouldCreateNewUserWhenNotFoundBySocialProvider() {
        OAuth2UserProfile userProfile = OAuth2UserProfile.withGoogleProvider(TEST_PROVIDER_ID)
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .pictureUrl(TEST_PICTURE)
                .build();

        User user = User.createFromOAuth2Profile(userProfile);

        when(userRepository.findBySocialProvider("google", TEST_PROVIDER_ID))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userAccountService.findOrCreateFromOAuth2(userProfile);

        assertThat(result).isEqualTo(user);
        verify(userRepository).findBySocialProvider("google", "123456789");
        verify(userRepository).save(any(User.class));
    }
}
