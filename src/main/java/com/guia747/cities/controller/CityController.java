package com.guia747.cities.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.cities.dto.CityResponse;
import com.guia747.cities.dto.CreateCityRequest;
import com.guia747.cities.dto.GetAllCitiesResponse;
import com.guia747.cities.entity.City;
import com.guia747.cities.service.CityManagementService;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityManagementService cityManagementService;

    public CityController(CityManagementService cityManagementService) {
        this.cityManagementService = cityManagementService;
    }

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CreateCityRequest request) {
        City city = cityManagementService.createCity(request);
        CityResponse response = CityResponse.from(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetAllCitiesResponse> getAllCities(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(0) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<City> cities = cityManagementService.getAllCities(pageable);
        Page<CityResponse> cityResponses = cities.map(CityResponse::from);

        GetAllCitiesResponse response = GetAllCitiesResponse.from(cityResponses);
        return ResponseEntity.ok(response);
    }
}
