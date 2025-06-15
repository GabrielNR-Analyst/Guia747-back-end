package com.guia747.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.guia747.cities.City;
import com.guia747.infrastructure.config.GlobalMapperConfig;
import com.guia747.infrastructure.persistence.jpa.entity.CityJpaEntity;

@Mapper(config = GlobalMapperConfig.class)
public interface CityMapper {

    City toCity(CityJpaEntity entity);

    CityJpaEntity toEntity(City city);
}
