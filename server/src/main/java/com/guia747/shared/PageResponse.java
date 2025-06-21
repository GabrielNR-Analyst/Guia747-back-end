package com.guia747.shared;

import java.util.List;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "A generic paginated response structure for API data, including content and pagination metadata.")
public record PageResponse<T>(List<T> data, com.guia747.shared.PageResponse.PaginationMetadata metadata) {

    public static <T> PageResponse<T> from(Page<T> page) {
        PaginationMetadata metadata = new PaginationMetadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious(),
                page.isFirst(),
                page.isLast()
        );

        return new PageResponse<>(page.getContent(), metadata);
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "Metadata for pagination, providing details about the current page and overall collection.")
    public static class PaginationMetadata {

        @Schema(description = "The current page number (0-based index)", example = "0")
        private int currentPage;
        @Schema(description = "The number of items per page", example = "0")
        private int itemsPerPage;
        @Schema(description = "The total number of items across all pages", example = "100")
        private long totalItems;
        @Schema(description = "The total number of pages available", example = "10")
        private int totalPages;
        @Schema(description = "Indicates if there is a next page", example = "true")
        private boolean hasNext;
        @Schema(description = "Indicates if there is a previous page", example = "false")
        private boolean hasPrevious;
        @Schema(description = "Indicates if this is the first page", example = "true")
        private boolean isFirst;
        @Schema(description = "Indicates if this is the last page", example = "false")
        private boolean isLast;
    }
}
