package com.guia747.authentication;

public class OAuth2UserInfo {

    private final String providerId;
    private final String name;
    private final String email;
    private final String profilePictureUrl;

    private OAuth2UserInfo(Builder builder) {
        this.providerId = builder.providerId;
        this.name = builder.name;
        this.email = builder.email;
        this.profilePictureUrl = builder.profilePictureUrl;
    }

    public String getProviderId() {
        return providerId;
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

    @Override
    public String toString() {
        return "OAuth2UserInfo{" +
                "providerId='" + providerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }

    public static Builder withProvider(String providerId) {
        return new Builder(providerId);
    }

    public static class Builder {

        private final String providerId;
        private String name;
        private String email;
        private String profilePictureUrl;

        private Builder(String providerId) {
            this.providerId = providerId;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder profilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
            return this;
        }

        public OAuth2UserInfo build() {
            return new OAuth2UserInfo(this);
        }
    }
}
