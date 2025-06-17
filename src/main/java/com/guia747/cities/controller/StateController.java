package com.guia747.cities.controller;

import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.cities.dto.CityResponse;
import com.guia747.cities.dto.GetAllCitiesByStateResponse;
import com.guia747.cities.dto.StateResponse;
import com.guia747.cities.entity.City;
import com.guia747.cities.entity.State;
import com.guia747.cities.service.CityManagementService;
import com.guia747.cities.service.StateManagementService;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    private final StateManagementService stateManagementService;
    private final CityManagementService cityManagementService;

    public StateController(StateManagementService stateManagementService, CityManagementService cityManagementService) {
        this.stateManagementService = stateManagementService;
        this.cityManagementService = cityManagementService;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> getAllStates() {
        List<State> allStates = stateManagementService.getAllStates();
        List<StateResponse> response = allStates.stream().map(StateResponse::from).toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{uf}/cities")
    public ResponseEntity<GetAllCitiesByStateResponse> getAllCitiesByUf(
            @PathVariable String uf,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(0) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<City> cities = cityManagementService.getAllCitiesByUf(uf.toUpperCase(), pageable);
        Page<CityResponse> cityResponses = cities.map(CityResponse::from);

        GetAllCitiesByStateResponse response = GetAllCitiesByStateResponse.from(cityResponses);
        return ResponseEntity.ok(response);
    }
}
