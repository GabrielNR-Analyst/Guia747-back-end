package com.guia747.domain.entity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class RefreshTokenSession {

    private final String refreshToken;
    private final String jwtId;
    private final UUID accountId;
    private final Instant issuedAt;
    private final Instant expiresAt;

    public RefreshTokenSession(String refreshToken, String jwtId, UUID accountId, Duration ttl) {
        this.refreshToken = refreshToken;
        this.jwtId = jwtId;
        this.accountId = accountId;
        this.issuedAt = Instant.now();
        this.expiresAt = this.issuedAt.plus(ttl);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getJwtId() {
        return jwtId;
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
