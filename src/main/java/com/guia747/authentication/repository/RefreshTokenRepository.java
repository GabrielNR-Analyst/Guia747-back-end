package com.guia747.authentication.repository;

import java.time.Duration;
import java.util.Optional;
import com.guia747.authentication.domain.RefreshTokenSession;

public interface RefreshTokenRepository {

    void saveRefreshToken(RefreshTokenSession refreshToken, Duration ttl);

    Optional<RefreshTokenSession> findByRefreshToken(String refreshToken);

    void deleteRefreshToken(String refreshToken);
}
