package com.guia747.authentication;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.authentication.usecase.OAuth2AuthenticationUseCase;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/oauth2")
public class OAuth2AuthenticationController {

    private final OAuth2AuthenticationUseCase oauth2AuthenticationUseCase;

    public OAuth2AuthenticationController(OAuth2AuthenticationUseCase oauth2AuthenticationUseCase) {
        this.oauth2AuthenticationUseCase = oauth2AuthenticationUseCase;
    }

    @Operation(summary = "Authenticate with OAuth2 authorization code")
    @PostMapping("/loginWithGoogle")
    public ResponseEntity<?> loginWithGoogle(@Valid @RequestBody OAuth2AuthenticationRequest request) {
        oauth2AuthenticationUseCase.execute(request);
        return ResponseEntity.ok().build();
    }
}
