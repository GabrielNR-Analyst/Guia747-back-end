package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.guia747.cities.entity.City;
import com.guia747.cities.vo.Image;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;

@Mapper(config = GlobalMapperConfig.class, uses = {StateMapper.class})
public interface CityMapper {

    @Mapping(target = "thumbnail", expression = "java(mapToThumbnailImage(entity))")
    @Mapping(target = "banner", expression = "java(mapToBannerImage(entity))")
    City toDomain(JpaCityEntity entity);

    @Mapping(target = "thumbnailUrl", source = "thumbnail.url")
    @Mapping(target = "thumbnailWidth", source = "thumbnail.width")
    @Mapping(target = "thumbnailHeight", source = "thumbnail.height")
    @Mapping(target = "bannerUrl", source = "banner.url")
    @Mapping(target = "bannerWidth", source = "banner.width")
    @Mapping(target = "bannerHeight", source = "banner.height")
    JpaCityEntity toEntity(City city);

    default Image mapToThumbnailImage(JpaCityEntity entity) {
        if (entity.getThumbnailUrl() == null) {
            return null;
        }
        return new Image(entity.getThumbnailUrl(), entity.getThumbnailWidth(), entity.getThumbnailHeight());
    }

    default Image mapToBannerImage(JpaCityEntity entity) {
        if (entity.getBannerUrl() == null) {
            return null;
        }
        return new Image(entity.getBannerUrl(), entity.getBannerWidth(), entity.getBannerHeight());
    }
}
