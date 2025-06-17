package com.guia747.cities.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCityRequest(
        @NotBlank @Size(max = 32) String name,
        @NotNull UUID stateId,
        @NotBlank @Size(min = 30, max = 320) String description,
        ImageRequest thumbnail,
        ImageRequest banner
) {

}
