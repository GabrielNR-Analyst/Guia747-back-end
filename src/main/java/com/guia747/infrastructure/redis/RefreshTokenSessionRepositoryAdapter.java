package com.guia747.infrastructure.redis;

import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.guia747.domain.entity.RefreshTokenSession;
import com.guia747.domain.repository.RefreshTokenSessionRepository;

@Repository
public class RefreshTokenSessionRepositoryAdapter implements RefreshTokenSessionRepository {

    private static final String TOKEN_KEY_PREFIX = "refresh_token:";
    private final RedisTemplate<String, Object> redisTemplate;

    public RefreshTokenSessionRepositoryAdapter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(RefreshTokenSession session) {
        String tokenKey = TOKEN_KEY_PREFIX + session.getRefreshToken();

        Duration ttl = Duration.between(session.getIssuedAt(), session.getExpiresAt());
        redisTemplate.opsForValue().set(tokenKey, session, ttl);
    }

    @Override
    public Optional<RefreshTokenSession> findByToken(String token) {
        String tokenKey = TOKEN_KEY_PREFIX + token;

        Object value = redisTemplate.opsForValue().get(tokenKey);
        if (value instanceof RefreshTokenSession session) {
            return Optional.of(session);
        }

        return Optional.empty();
    }
}
