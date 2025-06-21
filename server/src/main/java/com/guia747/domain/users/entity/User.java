package com.guia747.domain.users.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import com.guia747.shared.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class User extends AggregateRoot<UUID> {

    private final String name;
    private final String email;
    private final String profilePictureUrl;
    private final String providerId;
    private final String providerName;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static User createNew(String name, String email) {
        return new User(name, email, null, null, null, null, null);
    }

    public static User createFromOAuth2Profile(OAuth2UserProfile userProfile) {
        return new User(userProfile.getName(), userProfile.getEmail(), userProfile.getPictureUrl(),
                userProfile.getProviderId(), userProfile.getProviderName(), null, null);
    }
}
