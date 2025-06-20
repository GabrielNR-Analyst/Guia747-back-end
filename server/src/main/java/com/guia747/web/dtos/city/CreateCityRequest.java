package com.guia747.web.dtos.city;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating a city")
public record CreateCityRequest(
        @NotBlank
        @Size(max = 32)
        @Schema(description = "Name of the city", example = "São Paulo")
        String name,

        @NotNull
        @Schema(description = "ID of the state where the city is located")
        UUID stateId,

        @NotBlank
        @Size(min = 10, max = 320)
        @Schema(description = "Short description of the city", example = "Largest city in Brazil.")
        String description,

        @URL
        @Schema(description = "Thumbnail image url")
        String thumbnailUrl,

        @URL
        @Schema(description = "Banner image url")
        String bannerUrl
) {

}
