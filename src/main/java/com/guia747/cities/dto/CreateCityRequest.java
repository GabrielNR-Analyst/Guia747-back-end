package com.guia747.cities.dto;

import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating a city")
public record CreateCityRequest(
        @Schema(description = "Name of the city", example = "São Paulo")
        @NotBlank @Size(max = 32) String name,
        @Schema(description = "ID of the state where the city is located")
        @NotNull UUID stateId,
        @Schema(description = "Short description of the city", example = "Largest city in Brazil.")
        @NotBlank @Size(min = 10, max = 320) String description,
        @Schema(description = "Thumbnail image information")
        @Valid ImageRequest thumbnail,
        @Schema(description = "Banner image information")
        @Valid ImageRequest banner
) {

}
