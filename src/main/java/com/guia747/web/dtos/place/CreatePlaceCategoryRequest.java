package com.guia747.web.dtos.place;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating a category")
public record CreatePlaceCategoryRequest(
        @NotBlank
        @Schema(description = "Name of the category", example = "Coffee")
        String name,
        @Schema(description = "Short description of the category", example = "Coffee shops in the city")
        String description
) {

}
