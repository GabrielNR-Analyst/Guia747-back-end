package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.guia747.cities.entity.City;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;

@Mapper(config = GlobalMapperConfig.class, uses = {StateMapper.class})
public interface CityMapper {

    @Mapping(target = "thumbnail.url", source = "thumbnailUrl")
    @Mapping(target = "thumbnail.width", source = "thumbnailWidth")
    @Mapping(target = "thumbnail.height", source = "thumbnailHeight")
    @Mapping(target = "banner.url", source = "bannerUrl")
    @Mapping(target = "banner.width", source = "bannerWidth")
    @Mapping(target = "banner.height", source = "bannerHeight")
    City toDomain(JpaCityEntity entity);

    @Mapping(target = "thumbnailUrl", source = "thumbnail.url")
    @Mapping(target = "thumbnailUrl", source = "thumbnail.url")
    @Mapping(target = "thumbnailWidth", source = "thumbnail.width")
    @Mapping(target = "thumbnailHeight", source = "thumbnail.height")
    @Mapping(target = "bannerUrl", source = "banner.url")
    @Mapping(target = "bannerWidth", source = "banner.width")
    @Mapping(target = "bannerHeight", source = "banner.height")
    JpaCityEntity toEntity(City city);
}
