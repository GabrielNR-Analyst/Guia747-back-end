package com.guia747.accounts;

import com.guia747.authentication.OAuth2UserProfile;

public class UserAccount extends Entity {

    private final String name;
    private final String email;
    private final String profilePictureUrl;
    private final String providerId;
    private final String providerName;

    public UserAccount(String name, String email, String profilePictureUrl, String providerId, String providerName) {
        this.name = name;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public static UserAccount createFromOAuth2Profile(OAuth2UserProfile userProfile) {
        return new UserAccount(userProfile.getName(), userProfile.getEmail(), userProfile.getPictureUrl(),
                userProfile.getProviderId(), userProfile.getProviderName());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }
}
