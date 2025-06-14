package com.guia747.accounts;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.authentication.OAuth2UserProfile;

@Service
public class DefaultUserAccountService implements UserAccountService {

    private final UserRepository userRepository;

    public DefaultUserAccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserAccount findOrCreateFromOAuth2(OAuth2UserProfile oauth2User) {
        // Try to find existing user by social provider first
        Optional<UserAccount> existingUser = userRepository.findBySocialProvider(
                oauth2User.getProviderName().toLowerCase(), oauth2User.getProviderId());

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // Create new user from OAuth2 profile
        UserAccount userAccount = UserAccount.createFromOAuth2Profile(oauth2User);
        return userRepository.save(userAccount);
    }
}
