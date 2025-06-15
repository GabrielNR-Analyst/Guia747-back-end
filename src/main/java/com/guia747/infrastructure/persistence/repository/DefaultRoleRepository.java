package com.guia747.infrastructure.persistence.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.guia747.accounts.domain.Role;
import com.guia747.accounts.domain.RoleRepository;
import com.guia747.infrastructure.persistence.jpa.repository.RoleJpaRepository;
import com.guia747.infrastructure.persistence.mapper.RoleMapper;

@Repository
public class DefaultRoleRepository implements RoleRepository {

    private final RoleJpaRepository jpaRepository;
    private final RoleMapper roleMapper;

    public DefaultRoleRepository(RoleJpaRepository jpaRepository, RoleMapper roleMapper) {
        this.jpaRepository = jpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRepository.findByName(name).map(roleMapper::toRole);
    }
}
