package com.guia747.infrastructure.redis;

import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.guia747.authentication.domain.RefreshTokenSession;
import com.guia747.authentication.repository.RefreshTokenRepository;

@Repository
public class DefaultRefreshTokenRepository implements RefreshTokenRepository {

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";
    private final RedisTemplate<String, Object> redisTemplate;

    public DefaultRefreshTokenRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveRefreshToken(RefreshTokenSession refreshToken, Duration ttl) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken.getTokenValue();
        redisTemplate.opsForValue().set(key, refreshToken, ttl);
    }

    @Override
    public Optional<RefreshTokenSession> findByRefreshToken(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        return Optional.ofNullable((RefreshTokenSession) redisTemplate.opsForValue().get(key));
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        redisTemplate.delete(key);
    }
}
