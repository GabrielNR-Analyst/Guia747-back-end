package com.guia747.domain.entity;

import com.guia747.domain.vo.GoogleUserInfo;

public class UserAccount extends AggregateRoot {

    private String email;
    private String name;
    private String profilePictureUrl;

    private String googleId;

    public UserAccount(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static UserAccount createFromGoogleAuth(GoogleUserInfo googleUserInfo) {
        var account = new UserAccount(googleUserInfo.email(), googleUserInfo.name());
        account.googleId = googleUserInfo.googleId();
        account.profilePictureUrl = googleUserInfo.pictureUrl();

        return account;
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
