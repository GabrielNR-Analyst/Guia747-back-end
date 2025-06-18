package com.guia747.places.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.places.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing category details")
public record CategoryResponse(
        @Schema(description = "Unique identifier of the category")
        UUID id,
        @Schema(description = "Name of the category")
        String name,
        @Schema(description = "Unique slug for the category", example = "restaurant")
        String slug,
        @Schema(description = "Short description of the category")
        String description,
        @Schema(description = "Timestamp when the category was created")
        LocalDateTime createdAt,
        @Schema(description = "Timestamp when the category was last updated")
        LocalDateTime updatedAt
) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
