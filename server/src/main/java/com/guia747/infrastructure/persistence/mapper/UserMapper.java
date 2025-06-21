package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.domain.users.entity.User;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.UserJpaEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {

    UserJpaEntity toEntity(User user);

    User toUserAccount(UserJpaEntity userJpaEntity);
}
