package com.guia747.cities.dto;

import java.util.UUID;
import com.guia747.cities.entity.City;
import com.guia747.cities.vo.Image;

public record CityResponse(
        UUID id,
        String name,
        StateResponse state,
        String slug,
        String description,
        String about,
        ImageResponse thumbnail,
        ImageResponse banner
) {

    public static CityResponse from(City city) {
        StateResponse state = StateResponse.from(city.getState());

        ImageResponse thumbnail = null;
        ImageResponse banner = null;

        if (city.getThumbnail() != null) {
            thumbnail = getImageResponseFromImage(city.getThumbnail());
        }
        if (city.getBanner() != null) {
            banner = getImageResponseFromImage(city.getBanner());
        }

        return new CityResponse(
                city.getId(),
                city.getName(),
                state,
                city.getSlug(),
                city.getDescription(),
                city.getAbout(),
                thumbnail,
                banner
        );
    }

    private static ImageResponse getImageResponseFromImage(Image image) {
        return new ImageResponse(image.getUrl(), image.getWidth(), image.getHeight());
    }
}
