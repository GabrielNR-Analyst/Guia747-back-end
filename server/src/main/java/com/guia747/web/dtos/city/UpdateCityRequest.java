package com.guia747.web.dtos.city;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request DTO for updating an existing City")
public record UpdateCityRequest(
        @Schema(description = "Short description of the city", example = "Largest city in Brazil.")
        @Size(min = 10, max = 320)
        String description,

        @Size(max = 2000)
        @Schema(description = "Detailed information about the city", example = "São Paulo is a sprawling metropolis...")
        String about,

        @URL
        @Schema(description = "Thumbnail image url")
        String thumbnailUrl,

        @URL
        @Schema(description = "Banner image url")
        String bannerUrl
) {

}
