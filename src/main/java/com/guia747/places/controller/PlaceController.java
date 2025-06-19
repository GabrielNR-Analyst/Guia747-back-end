package com.guia747.places.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.places.dto.CategoryResponse;
import com.guia747.places.dto.CreateCategoryRequest;
import com.guia747.places.dto.CreatePlaceRequest;
import com.guia747.places.dto.CreatePlaceResponse;
import com.guia747.places.dto.GetAllPlacesResponse;
import com.guia747.places.dto.PlaceDetailsResponse;
import com.guia747.places.dto.UpdatePlaceRequest;
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
    public ResponseEntity<CreatePlaceResponse> create(@Valid @RequestBody CreatePlaceRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Place place = placeManagementService.createPlace(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatePlaceResponse(place.getId()));
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<Void> updatePlace(@PathVariable UUID placeId,
            @Valid @RequestBody UpdatePlaceRequest request) {
        placeManagementService.updatePlace(placeId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailsResponse> getPlace(@PathVariable UUID placeId) {
        PlaceDetailsResponse placeDetailsResponse = placeManagementService.getPlaceDetail(placeId);
        return ResponseEntity.ok(placeDetailsResponse);
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
    public ResponseEntity<GetAllPlacesResponse> getAllPlacesByCity(
            @PathVariable UUID cityId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Place> placePage = placeManagementService.getAllPlacesByCity(cityId, pageable);
        Page<PlaceDetailsResponse> responsePage = placePage.map(PlaceDetailsResponse::from);

        GetAllPlacesResponse response = GetAllPlacesResponse.from(responsePage);
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
