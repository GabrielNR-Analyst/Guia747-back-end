package com.guia747.authentication;

public class OAuth2UserProfile {

    private final String providerId;
    private final String providerName;
    private final String email;
    private final String name;
    private final String pictureUrl;

    public OAuth2UserProfile(Builder builder) {
        this.providerId = builder.providerId;
        this.providerName = builder.providerName;
        this.email = builder.email;
        this.name = builder.name;
        this.pictureUrl = builder.pictureUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public static Builder withGoogleProvider(String providerId) {
        return new Builder("Google", providerId);
    }

    public static class Builder {

        private final String providerName;
        private final String providerId;
        private String email;
        private String name;
        private String pictureUrl;

        private Builder(String providerName, String providerId) {
            this.providerName = providerName;
            this.providerId = providerId;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
            return this;
        }

        public OAuth2UserProfile build() {
            return new OAuth2UserProfile(this);
        }
    }
}
