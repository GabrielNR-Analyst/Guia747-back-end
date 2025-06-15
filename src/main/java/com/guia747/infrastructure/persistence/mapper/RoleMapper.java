package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.accounts.domain.Role;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.RoleJpaEntity;

@Mapper(config = GlobalMapperConfig.class, uses = {PermissionMapper.class})
public interface RoleMapper {

    RoleJpaEntity toEntity(Role role);

    Role toRole(RoleJpaEntity entity);
}
