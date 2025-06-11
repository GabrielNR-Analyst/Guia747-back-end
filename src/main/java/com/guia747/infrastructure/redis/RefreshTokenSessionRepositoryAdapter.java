package com.guia747.infrastructure.redis;

import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guia747.domain.entity.RefreshTokenSession;
import com.guia747.domain.repository.RefreshTokenSessionRepository;

@Repository
public class RefreshTokenSessionRepositoryAdapter implements RefreshTokenSessionRepository {

    private static final String TOKEN_KEY_PREFIX = "refresh_token:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RefreshTokenSessionRepositoryAdapter(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
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
        RefreshTokenSession session = objectMapper.convertValue(value, RefreshTokenSession.class);
        return Optional.ofNullable(session);
    }

    @Override
    public void deleteByToken(String token) {
        String tokenKey = TOKEN_KEY_PREFIX + token;
        redisTemplate.delete(tokenKey);
    }
}
