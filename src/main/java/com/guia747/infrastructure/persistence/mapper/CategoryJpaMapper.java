package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCategoryEntity;
import com.guia747.places.entity.Category;

@Mapper(config = GlobalMapperConfig.class)
public interface CategoryJpaMapper {

    Category toDomain(JpaCategoryEntity entity);

    JpaCategoryEntity toEntity(Category category);
}
