package com.guia747.domain.repository;

import java.util.Optional;
import com.guia747.domain.entity.RefreshTokenSession;

public interface RefreshTokenSessionRepository {

    void save(RefreshTokenSession session);

    Optional<RefreshTokenSession> findByToken(String token);

    void deleteByToken(String token);
}
