package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.accounts.domain.Permission;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.PermissionJpaEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface PermissionMapper {

    Permission toPermission(PermissionJpaEntity entity);

    PermissionJpaEntity toEntity(Permission permission);
}
