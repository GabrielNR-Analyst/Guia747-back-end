package com.guia747.web.dtos.place;

import com.guia747.domain.places.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing category details")
public record CategoryResponse(
        @Schema(description = "Name of the category")
        String name,
        @Schema(description = "Unique slug for the category", example = "restaurant")
        String slug,
        @Schema(description = "Short description of the category")
        String description
) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getName(),
                category.getSlug(),
                category.getDescription()
        );
    }
}
