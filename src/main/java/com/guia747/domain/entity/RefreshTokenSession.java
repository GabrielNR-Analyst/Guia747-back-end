package com.guia747.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class RefreshTokenSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String refreshToken;
    private UUID accountId;
    private Instant issuedAt;
    private Instant expiresAt;

    protected RefreshTokenSession() {
    }

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
