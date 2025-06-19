package com.guia747.application.city.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.city.service.ImageService;
import com.guia747.domain.city.entity.City;
import com.guia747.domain.city.entity.State;
import com.guia747.domain.city.exception.StateNotFoundException;
import com.guia747.domain.city.repository.CityRepository;
import com.guia747.domain.city.repository.StateRepository;
import com.guia747.domain.city.valueobject.Image;
import com.guia747.infrastructure.util.SlugGenerator;
import com.guia747.web.dtos.city.CreateCityRequest;
import com.guia747.web.dtos.city.CreateCityResponse;

@Service
public class DefaultCreateCityUseCase implements CreateCityUseCase {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final ImageService imageService;

    public DefaultCreateCityUseCase(
            CityRepository cityRepository,
            StateRepository stateRepository,
            ImageService imageService
    ) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public CreateCityResponse execute(CreateCityRequest request) {
        State state = stateRepository.findById(request.stateId()).orElseThrow(StateNotFoundException::new);

        Image thumbnail = imageService.resolve(request.thumbnailUrl());
        Image banner = imageService.resolve(request.bannerUrl());

        String slug = SlugGenerator.generateSlug(request.name());

        City newCity = City.createNew(request.name(), state, slug, request.description());
        newCity.updateImages(thumbnail, banner);

        City savedCity = cityRepository.save(newCity);
        return new CreateCityResponse(savedCity.getId());
    }
}
