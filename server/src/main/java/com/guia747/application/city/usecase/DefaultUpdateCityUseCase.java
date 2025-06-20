package com.guia747.application.city.usecase;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.city.service.ImageService;
import com.guia747.web.dtos.city.UpdateCityRequest;
import com.guia747.domain.city.entity.City;
import com.guia747.domain.city.exception.CityNotFoundException;
import com.guia747.domain.city.repository.CityRepository;
import com.guia747.domain.city.valueobject.Image;
import com.guia747.web.dtos.city.CityDetailsResponse;

@Service
public class DefaultUpdateCityUseCase implements UpdateCityUseCase {

    private final CityRepository cityRepository;
    private final ImageService imageService;

    public DefaultUpdateCityUseCase(CityRepository cityRepository, ImageService imageService) {
        this.cityRepository = cityRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public CityDetailsResponse execute(UUID cityId, UpdateCityRequest request) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);

        city.updateDetails(request.description(), request.about());

        Image thumbnail = imageService.resolve(request.thumbnailUrl());
        Image banner = imageService.resolve(request.bannerUrl());

        city.updateImages(thumbnail, banner);

        City savedCity = cityRepository.save(city);
        return CityDetailsResponse.from(savedCity);
    }
}
