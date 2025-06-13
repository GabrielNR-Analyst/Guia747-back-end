package com.guia747.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;
import com.guia747.accounts.UserAccount;
import com.guia747.accounts.UserAccountRepository;
import com.guia747.infrastructure.persistence.jpa.entity.UserAccountJpaEntity;
import com.guia747.infrastructure.persistence.jpa.repository.UserJpaRepository;
import com.guia747.infrastructure.persistence.mapper.UserAccountMapper;

@Repository
public class DefaultUserAccountRepository implements UserAccountRepository {

    private final UserJpaRepository jpaRepository;
    private final UserAccountMapper mapper;

    public DefaultUserAccountRepository(UserJpaRepository jpaRepository, UserAccountMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        UserAccountJpaEntity entity = jpaRepository.save(mapper.toEntity(userAccount));
        return mapper.toUserAccount(entity);
    }
}
