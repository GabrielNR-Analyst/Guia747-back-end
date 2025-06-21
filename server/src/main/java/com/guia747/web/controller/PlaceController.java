package com.guia747.web.controller;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.place.query.GetAllCategoriesQueryHandler;
import com.guia747.application.place.query.GetAllReviewsByPlaceIdHandler;
import com.guia747.application.place.query.GetPlaceDetailsQuery;
import com.guia747.application.place.query.GetPlaceDetailsQueryHandler;
import com.guia747.application.place.query.GetPlacesByCityQuery;
import com.guia747.application.place.query.GetPlacesByCityQueryHandler;
import com.guia747.application.place.usecase.CreatePlaceCategoryUseCase;
import com.guia747.application.place.usecase.CreatePlaceUseCase;
import com.guia747.application.place.usecase.CreateReviewUseCase;
import com.guia747.application.place.usecase.DeleteReviewUseCase;
import com.guia747.application.place.usecase.UpdatePlaceUseCase;
import com.guia747.shared.PageResponse;
import com.guia747.web.dtos.place.CategoryResponse;
import com.guia747.web.dtos.place.CreatePlaceCategoryRequest;
import com.guia747.web.dtos.place.CreatePlaceRequest;
import com.guia747.web.dtos.place.CreatePlaceResponse;
import com.guia747.web.dtos.place.PlaceDetailsResponse;
import com.guia747.web.dtos.place.UpdatePlaceRequest;
import com.guia747.domain.places.entity.Place;
import com.guia747.web.dtos.reviews.CreateReviewRequest;
import com.guia747.web.dtos.reviews.ReviewDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class PlaceController {

    private final CreatePlaceUseCase createPlaceUseCase;
    private final UpdatePlaceUseCase updatePlaceUseCase;
    private final GetPlacesByCityQueryHandler getPlacesByCityQueryHandler;
    private final GetPlaceDetailsQueryHandler getPlaceDetailsQueryHandler;
    private final CreatePlaceCategoryUseCase createPlaceCategoryUseCase;
    private final GetAllCategoriesQueryHandler getAllCategoriesQueryHandler;
    private final CreateReviewUseCase createReviewUseCase;
    private final GetAllReviewsByPlaceIdHandler getAllReviewsByPlaceIdHandler;
    private final DeleteReviewUseCase deleteReviewUseCase;

    @PostMapping
    public ResponseEntity<CreatePlaceResponse> create(
            @Valid @RequestBody CreatePlaceRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID authenticatedUserId = UUID.fromString(jwt.getSubject());
        CreatePlaceResponse response = createPlaceUseCase.execute(authenticatedUserId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<Void> updatePlace(
            @PathVariable UUID placeId,
            @Valid @RequestBody UpdatePlaceRequest request
    ) {
        updatePlaceUseCase.execute(placeId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailsResponse> getPlace(@PathVariable UUID placeId) {
        GetPlaceDetailsQuery query = new GetPlaceDetailsQuery(placeId);
        PlaceDetailsResponse response = getPlaceDetailsQueryHandler.handle(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{placeId}/reviews")
    public ResponseEntity<ReviewDetailsResponse> createReview(
            @PathVariable UUID placeId,
            @Valid @RequestBody CreateReviewRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        ReviewDetailsResponse response = createReviewUseCase.execute(placeId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{placeId}/reviews")
    public ResponseEntity<PageResponse<ReviewDetailsResponse>> getAllReviewsFromPlace(
            @PathVariable UUID placeId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<ReviewDetailsResponse> pageResponse = getAllReviewsByPlaceIdHandler.handle(placeId, pageable);
        PageResponse<ReviewDetailsResponse> response = PageResponse.from(pageResponse);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        deleteReviewUseCase.execute(reviewId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "List all places in a city",
            description = "Get all places located in a specific city with pagination and sorting."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Places retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content),
    })
    @GetMapping("/cities/{cityId}/places")
    public ResponseEntity<PageResponse<PlaceDetailsResponse>> getAllPlacesByCity(
            @PathVariable UUID cityId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        GetPlacesByCityQuery query = new GetPlacesByCityQuery(cityId, pageable);
        Page<Place> placesPage = getPlacesByCityQueryHandler.handle(query);

        Page<PlaceDetailsResponse> responsePage = placesPage.map(PlaceDetailsResponse::from);

        PageResponse<PlaceDetailsResponse> response = PageResponse.from(responsePage);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully", content = @Content(
                    schema = @Schema(implementation = CategoryResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content),
    })
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreatePlaceCategoryRequest request) {
        CategoryResponse response = createPlaceCategoryUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "List all categories",
            description = "Get all categories in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> response = getAllCategoriesQueryHandler.handle();
        return ResponseEntity.ok(response);
    }
}
