package com.guia747.cities.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.cities.dto.CreateCityRequest;
import com.guia747.cities.dto.ImageRequest;
import com.guia747.cities.dto.UpdateCityRequest;
import com.guia747.cities.entity.City;
import com.guia747.cities.entity.State;
import com.guia747.cities.exception.CityNotFoundException;
import com.guia747.cities.exception.StateNotFoundException;
import com.guia747.cities.repository.CityRepository;
import com.guia747.cities.repository.StateRepository;
import com.guia747.cities.vo.Image;
import com.guia747.infrastructure.util.SlugGenerator;

@Service
public class DefaultCityManagementService implements CityManagementService {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public DefaultCityManagementService(StateRepository stateRepository, CityRepository cityRepository) {
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional
    public City createCity(CreateCityRequest request) {
        State state = stateRepository.findById(request.stateId()).orElseThrow(StateNotFoundException::new);

        Image thumbnail = getImageFromUrl(request.thumbnail());
        Image banner = getImageFromUrl(request.banner());

        String slug = SlugGenerator.generateSlug(request.name());

        City newCity = City.createNew(request.name(), state, slug, request.description());
        newCity.updateImages(thumbnail, banner);

        return cityRepository.save(newCity);
    }

    @Override
    @Transactional
    public Page<City> getAllCitiesByUf(String uf, Pageable pageable) {
        State state = stateRepository.findByUf(uf).orElseThrow(StateNotFoundException::new);

        return cityRepository.findAllByState(state, pageable);
    }

    @Override
    @Transactional
    @Cacheable(value = "allCities", key = "{#search, #pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    public Page<City> getAllCities(String search, Pageable pageable) {
        return cityRepository.findAll(search, pageable);
    }

    @Override
    @Transactional
    public City updateCity(UUID cityId, UpdateCityRequest request) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);

        city.updateDetails(request.description(), request.about());

        Image thumbnail = getImageFromUrl(request.thumbnail());
        Image banner = getImageFromUrl(request.banner());

        city.updateImages(thumbnail, banner);

        return cityRepository.save(city);
    }

    private Image getImageFromUrl(ImageRequest request) {
        if (request == null || request.url() == null || request.url().isBlank()) {
            return null;
        }

        Integer width = null;
        Integer height = null;
        try {
            URI uri = URI.create(request.url());
            URL imageUrl = uri.toURL();
            BufferedImage image = ImageIO.read(imageUrl);
            if (image != null) {
                width = image.getWidth();
                height = image.getHeight();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Image.createNew(request.url(), width, height);
    }
}
