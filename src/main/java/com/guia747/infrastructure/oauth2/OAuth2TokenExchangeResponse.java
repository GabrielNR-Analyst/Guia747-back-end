package com.guia747.infrastructure.oauth2;

import java.time.Instant;

public class OAuth2TokenExchangeResponse {

    private final String accessToken;
    private final String tokenType;
    private final String refreshToken;
    private final Instant expiresAt;

    public OAuth2TokenExchangeResponse(Builder builder) {
        this.accessToken = builder.accessToken;
        this.tokenType = builder.tokenType;
        this.refreshToken = builder.refreshToken;
        this.expiresAt = builder.expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public static Builder withToken(String accessToken) {
        return new Builder(accessToken);
    }

    public static class Builder {

        private final String accessToken;
        private String refreshToken;
        private String tokenType;
        private Instant expiresAt;

        private Builder(String accessToken) {
            this.accessToken = accessToken;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public OAuth2TokenExchangeResponse build() {
            return new OAuth2TokenExchangeResponse(this);
        }
    }
}
