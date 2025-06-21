package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.domain.places.entity.Review;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.JpaReviewEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface JpaReviewMapper {

    Review toDomain(JpaReviewEntity entity);

    JpaReviewEntity toEntity(Review review);
}
