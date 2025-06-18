package com.guia747.cities.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request DTO for updating an existing City")
public record UpdateCityRequest(
        @Schema(description = "Short description of the city", example = "Largest city in Brazil.")
        @Size(min = 10, max = 320)
        String description,
        @Schema(description = "Detailed information about the city", example = "São Paulo is a sprawling metropolis...")
        @Size(max = 2000)
        String about,
        @Schema(description = "Thumbnail image information")
        @Valid ImageRequest thumbnail,
        @Schema(description = "Banner image information")
        @Valid ImageRequest banner
) {

}
