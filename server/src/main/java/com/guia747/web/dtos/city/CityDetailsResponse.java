package com.guia747.web.dtos.city;

import java.util.UUID;
import com.guia747.web.dtos.ImageResponse;
import com.guia747.domain.city.entity.City;

public record CityDetailsResponse(
        UUID cityId,
        String name,
        StateResponse state,
        String slug,
        String description,
        String about,
        ImageResponse thumbnail,
        ImageResponse banner
) {

    public static CityDetailsResponse from(City city) {
        return new CityDetailsResponse(
                city.getId(),
                city.getName(),
                StateResponse.from(city.getState()),
                city.getSlug(),
                city.getDescription(),
                city.getAbout(),
                ImageResponse.from(city.getThumbnail()),
                ImageResponse.from(city.getBanner())
        );
    }
}
