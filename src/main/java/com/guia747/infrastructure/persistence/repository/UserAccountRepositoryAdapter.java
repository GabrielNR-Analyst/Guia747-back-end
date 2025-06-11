package com.guia747.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.repository.UserAccountRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaUserAccountEntity;
import com.guia747.infrastructure.persistence.jpa.repository.JpaUserAccountRepository;
import com.guia747.infrastructure.persistence.mapper.UserMapper;

@Repository
public class UserAccountRepositoryAdapter implements UserAccountRepository {

    private final JpaUserAccountRepository jpaRepository;
    private final UserMapper mapper;

    public UserAccountRepositoryAdapter(JpaUserAccountRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        JpaUserAccountEntity entity = mapper.toEntity(userAccount);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<UserAccount> findByGoogleId(String googleId) {
        return jpaRepository.findByGoogleId(googleId).map(mapper::toDomain);
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
