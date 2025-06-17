package com.guia747.cities.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.cities.dto.StateResponse;
import com.guia747.cities.entity.State;
import com.guia747.cities.service.StateManagementService;

@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    private final StateManagementService stateManagementService;

    public StateController(StateManagementService stateManagementService) {
        this.stateManagementService = stateManagementService;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> getAllStates() {
        List<State> allStates = stateManagementService.getAllStates();
        List<StateResponse> response = allStates.stream().map(StateResponse::from).toList();
        return ResponseEntity.ok().body(response);
    }
}
