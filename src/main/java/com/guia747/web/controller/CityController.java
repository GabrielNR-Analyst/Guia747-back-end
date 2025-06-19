package com.guia747.web.controller;

import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.city.query.GetAllCitiesQuery;
import com.guia747.application.city.query.GetAllCitiesQueryHandler;
import com.guia747.application.city.usecase.CreateCityUseCase;
import com.guia747.application.city.usecase.UpdateCityUseCase;
import com.guia747.web.dtos.city.GetAllCitiesResponse;
import com.guia747.web.dtos.city.UpdateCityRequest;
import com.guia747.web.dtos.city.CreateCityRequest;
import com.guia747.web.dtos.city.CityDetailsResponse;
import com.guia747.web.dtos.city.CreateCityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cities")
@Tag(name = "Cities", description = "City management endpoints")
@RequiredArgsConstructor
public class CityController {

    private final CreateCityUseCase createCityUseCase;
    private final UpdateCityUseCase updateCityUseCase;
    private final GetAllCitiesQueryHandler getAllCitiesQueryHandler;

    @Operation(
            summary = "Create a new city",
            description = "Creates a new city in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "City created successfully", content = @Content(
                    schema = @Schema(implementation = CreateCityResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "State not found", content = @Content),
    })
    @PostMapping
    public ResponseEntity<CreateCityResponse> createCity(@Valid @RequestBody CreateCityRequest request) {
        CreateCityResponse response = createCityUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update an existing city",
            description = "Updates an existing city in the system by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City updated successfully", content = @Content(
                    schema = @Schema(implementation = CreateCityResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "City or state not found", content = @Content),
    })
    @PutMapping("/{cityId}")
    public ResponseEntity<CityDetailsResponse> updateCityDetails(
            @Parameter(name = "ID of the city to update")
            @PathVariable UUID cityId,
            @Valid @RequestBody UpdateCityRequest request
    ) {
        CityDetailsResponse response = updateCityUseCase.execute(cityId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "List all cities (paginated, searchable, filterable)",
            description = "Get all cities in the system with pagination, sorting, search by name, and filter by UF"
    )
    @ApiResponse(responseCode = "200", description = "Cities retrieved successfully", content = @Content(
            schema = @Schema(implementation = GetAllCitiesResponse.class))
    )
    @GetMapping
    public ResponseEntity<GetAllCitiesResponse> getAllCities(
            @Parameter(description = "Search term for city name")
            @RequestParam(required = false) String search,
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "20") @Min(0) @Max(100) int size,
            @Parameter(description = "Sort by (name, state, createdAt)", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc, desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        GetAllCitiesQuery query = new GetAllCitiesQuery(search, pageable);
        Page<CityDetailsResponse> cityResponses = getAllCitiesQueryHandler.handle(query);

        GetAllCitiesResponse response = GetAllCitiesResponse.from(cityResponses);
        return ResponseEntity.ok(response);
    }
}
