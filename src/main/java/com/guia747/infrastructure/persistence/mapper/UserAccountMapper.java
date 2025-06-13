package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.accounts.UserAccount;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.UserAccountJpaEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface UserAccountMapper {

    UserAccountJpaEntity toEntity(UserAccount userAccount);

    UserAccount toUserAccount(UserAccountJpaEntity entity);
}
