package com.guia747.domain.entity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class RefreshTokenSession {

    private final String refreshToken;
    private final UUID accountId;
    private final Instant issuedAt;
    private final Instant expiresAt;

    public RefreshTokenSession(String refreshToken, UUID accountId, Duration ttl) {
        this.refreshToken = refreshToken;
        this.accountId = accountId;
        this.issuedAt = Instant.now();
        this.expiresAt = this.issuedAt.plus(ttl);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
