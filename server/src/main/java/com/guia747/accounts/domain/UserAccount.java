package com.guia747.accounts.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserAccount implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String name;
    private final String email;
    private final String profilePictureUrl;
    private final String providerId;
    private final String providerName;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserAccount createFromOAuth2Profile(OAuth2UserProfile userProfile) {
        return new UserAccount(null, userProfile.getName(), userProfile.getEmail(), userProfile.getPictureUrl(),
                userProfile.getProviderId(), userProfile.getProviderName(), null, null);
    }
}
