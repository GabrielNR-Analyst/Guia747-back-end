package com.guia747.infrastructure.persistence.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.guia747.accounts.domain.Permission;
import com.guia747.accounts.domain.PermissionRepository;
import com.guia747.infrastructure.persistence.jpa.repository.PermissionJpaRepository;
import com.guia747.infrastructure.persistence.mapper.PermissionMapper;

@Repository
public class DefaultPermissionRepository implements PermissionRepository {

    private final PermissionJpaRepository jpaRepository;
    private final PermissionMapper mapper;

    public DefaultPermissionRepository(PermissionJpaRepository jpaRepository, PermissionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toPermission);
    }
}
