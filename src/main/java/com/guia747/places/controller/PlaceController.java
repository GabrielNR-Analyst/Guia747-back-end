package com.guia747.places.controller;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.places.dto.CategoryResponse;
import com.guia747.places.dto.CreateCategoryRequest;
import com.guia747.places.dto.CreatePlaceRequest;
import com.guia747.places.dto.PlaceResponse;
import com.guia747.places.dto.PlaceDetailsResponse;
import com.guia747.places.entity.Category;
import com.guia747.places.entity.Place;
import com.guia747.places.service.CategoryManagementService;
import com.guia747.places.service.PlaceManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final CategoryManagementService categoryManagementService;
    private final PlaceManagementService placeManagementService;

    public PlaceController(CategoryManagementService categoryManagementService,
            PlaceManagementService placeManagementService) {
        this.categoryManagementService = categoryManagementService;
        this.placeManagementService = placeManagementService;
    }

    @PostMapping
    public ResponseEntity<PlaceResponse> create(@Valid @RequestBody CreatePlaceRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Place place = placeManagementService.createPlace(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PlaceResponse(place.getId()));
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailsResponse> getPlace(@PathVariable UUID placeId) {
        PlaceDetailsResponse placeDetailsResponse = placeManagementService.getPlaceDetail(placeId);
        return ResponseEntity.ok(placeDetailsResponse);
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
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category category = categoryManagementService.createCategory(request);
        CategoryResponse response = CategoryResponse.from(category);

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
        List<Category> allCategories = categoryManagementService.getAllCategories();
        List<CategoryResponse> response = allCategories.stream().map(CategoryResponse::from).toList();
        return ResponseEntity.ok(response);
    }
}
