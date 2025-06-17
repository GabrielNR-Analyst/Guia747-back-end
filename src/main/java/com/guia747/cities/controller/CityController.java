package com.guia747.cities.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.cities.dto.CityResponse;
import com.guia747.cities.dto.CreateCityRequest;
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
}
