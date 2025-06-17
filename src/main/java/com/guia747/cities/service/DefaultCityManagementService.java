package com.guia747.cities.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.cities.dto.CreateCityRequest;
import com.guia747.cities.dto.ImageRequest;
import com.guia747.cities.entity.City;
import com.guia747.cities.entity.State;
import com.guia747.cities.repository.CityRepository;
import com.guia747.cities.repository.StateRepository;
import com.guia747.cities.vo.Image;
import com.guia747.common.ResourceNotFoundException;
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
        State state = stateRepository.findById(request.stateId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado"));

        Image thumbnail = getImageFromUrl(request.thumbnail());
        Image banner = getImageFromUrl(request.banner());

        String slug = SlugGenerator.generateSlug(request.name());

        City newCity = City.createNew(request.name(), state, slug, request.description());
        newCity.updateImages(thumbnail, banner);

        return cityRepository.save(newCity);
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
