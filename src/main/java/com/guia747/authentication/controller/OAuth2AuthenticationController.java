package com.guia747.authentication.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.accounts.dto.UserAccountDetailsResponse;
import com.guia747.authentication.dto.AuthenticationResponse;
import com.guia747.authentication.dto.OAuth2AuthenticationRequest;
import com.guia747.authentication.usecase.OAuth2AuthenticationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/oauth2")
@Tag(name = "Authentication", description = "Base URL for authentication endpoints")
public class OAuth2AuthenticationController {

    private final OAuth2AuthenticationUseCase oauth2AuthenticationUseCase;

    public OAuth2AuthenticationController(OAuth2AuthenticationUseCase oauth2AuthenticationUseCase) {
        this.oauth2AuthenticationUseCase = oauth2AuthenticationUseCase;
    }

    @Operation(
            summary = "Authenticate with OAuth2 authorization code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(
                    schema = @Schema(implementation = UserAccountDetailsResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid input or missing parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content)
    })
    @PostMapping("/loginWithGoogle")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
            @Valid @RequestBody OAuth2AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = oauth2AuthenticationUseCase.execute(request);
        return ResponseEntity.ok(authenticationResponse);
    }
}
