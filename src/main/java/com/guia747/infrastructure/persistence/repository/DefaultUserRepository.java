package com.guia747.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.guia747.accounts.domain.UserAccount;
import com.guia747.accounts.domain.UserRepository;
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
    public UserAccount save(UserAccount userAccount) {
        UserJpaEntity entity = mapper.toEntity(userAccount);
        return mapper.toUserAccount(jpaRepository.save(entity));
    }

    @Override
    public Optional<UserAccount> findById(UUID accountId) {
        return jpaRepository.findById(accountId).map(mapper::toUserAccount);
    }

    @Override
    public Optional<UserAccount> findBySocialProvider(String providerName, String providerId) {
        return jpaRepository.findByProviderNameEqualsIgnoreCaseAndProviderId(providerName, providerId)
                .map(mapper::toUserAccount);
    }
}
