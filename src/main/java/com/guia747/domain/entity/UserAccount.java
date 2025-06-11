package com.guia747.domain.entity;

import com.guia747.domain.vo.SocialUserProfile;

public class UserAccount extends AggregateRoot {

    private String email;
    private String name;
    private String profilePictureUrl;

    private String googleId;

    public UserAccount(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static UserAccount createFromSocialProfile(SocialUserProfile socialUserProfile) {
        var account = new UserAccount(socialUserProfile.email(), socialUserProfile.name());
        account.googleId = socialUserProfile.providerId();
        account.profilePictureUrl = socialUserProfile.pictureUrl();

        return account;
    }

    public void reconnectSocialProfile(SocialUserProfile socialProfile) {
        this.googleId = socialProfile.providerId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
