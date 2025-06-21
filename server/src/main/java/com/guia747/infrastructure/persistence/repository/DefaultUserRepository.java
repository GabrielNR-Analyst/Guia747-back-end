package com.guia747.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.guia747.domain.users.entity.User;
import com.guia747.domain.users.repository.UserRepository;
import com.guia747.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.guia747.infrastructure.persistence.jpa.repository.UserJpaRepository;
import com.guia747.infrastructure.persistence.mapper.UserMapper;

@Repository
public class DefaultUserRepository implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    public DefaultUserRepository(UserJpaRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toEntity(user);
        return mapper.toUserAccount(jpaRepository.save(entity));
    }

    @Override
    public Optional<User> findById(UUID accountId) {
        return jpaRepository.findById(accountId).map(mapper::toUserAccount);
    }

    @Override
    public Optional<User> findBySocialProvider(String providerName, String providerId) {
        return jpaRepository.findByProviderNameEqualsIgnoreCaseAndProviderId(providerName, providerId)
                .map(mapper::toUserAccount);
    }
}
