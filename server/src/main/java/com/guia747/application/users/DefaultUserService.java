package com.guia747.application.users;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.users.entity.User;
import com.guia747.domain.users.repository.UserRepository;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User findOrCreateFromOAuth2(OAuth2UserProfile oauth2User) {
        // Try to find existing user by social provider first
        Optional<User> existingUser = userRepository.findBySocialProvider(
                oauth2User.getProviderName().toLowerCase(), oauth2User.getProviderId());

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // Create new user from OAuth2 profile
        User user = User.createFromOAuth2Profile(oauth2User);
        return userRepository.save(user);
    }
}
