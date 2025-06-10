package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.domain.entity.UserAccount;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.JpaUserAccountEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {

    UserAccount toDomain(JpaUserAccountEntity entity);

    JpaUserAccountEntity toEntity(UserAccount domain);
}
