package com.guia747.web.controller;

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
import com.guia747.application.city.query.GetAllCitiesByUfQuery;
import com.guia747.application.city.query.GetAllCitiesByUfQueryHandler;
import com.guia747.application.city.query.GetAllStatesQueryHandler;
import com.guia747.web.dtos.city.GetAllCitiesResponse;
import com.guia747.web.dtos.city.CityDetailsResponse;
import com.guia747.web.dtos.city.StateResponse;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    private final GetAllCitiesByUfQueryHandler getAllCitiesByUfQueryHandler;
    private final GetAllStatesQueryHandler getAllStatesQueryHandler;

    public StateController(
            GetAllCitiesByUfQueryHandler getAllCitiesByUfQueryHandler,
            GetAllStatesQueryHandler getAllStatesQueryHandler
    ) {
        this.getAllCitiesByUfQueryHandler = getAllCitiesByUfQueryHandler;
        this.getAllStatesQueryHandler = getAllStatesQueryHandler;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> getAllStates() {
        List<StateResponse> response = getAllStatesQueryHandler.handle();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{uf}/cities")
    public ResponseEntity<GetAllCitiesResponse> getAllCitiesByUf(
            @PathVariable String uf,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(0) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        GetAllCitiesByUfQuery query = new GetAllCitiesByUfQuery(uf.toUpperCase(), pageable);
        Page<CityDetailsResponse> cityResponses = getAllCitiesByUfQueryHandler.handle(query);

        GetAllCitiesResponse response = GetAllCitiesResponse.from(cityResponses);
        return ResponseEntity.ok(response);
    }
}
