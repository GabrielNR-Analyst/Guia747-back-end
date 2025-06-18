package com.guia747.cities.dto;

import jakarta.validation.constraints.Size;

public record UpdateCityRequest(
        @Size(min = 10, max = 320)
        String description,
        @Size(max = 2000)
        String about,
        ImageRequest thumbnail,
        ImageRequest banner
) {

}
