package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.domain.city.entity.State;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.JpaStateEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface StateMapper {

    State toDomain(JpaStateEntity entity);

    JpaStateEntity toEntity(State state);
}
