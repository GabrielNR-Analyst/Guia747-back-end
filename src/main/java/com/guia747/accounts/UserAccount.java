package com.guia747.accounts;

import com.guia747.authentication.OAuth2UserInfo;

public class UserAccount extends Entity {

    private final String name;
    private final String email;

    private final String providerName;
    private final String providerId;

    public UserAccount(String name, String email, String providerName, String providerId) {
        this.name = name;
        this.email = email;
        this.providerName = providerName;
        this.providerId = providerId;
    }

    public UserAccount createFromOAuth2UserInfo(OAuth2UserInfo userInfo) {
        return new UserAccount(userInfo.getName(), userInfo.getEmail(), userInfo.getProviderId(),
                userInfo.getProviderId());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderId() {
        return providerId;
    }
}
