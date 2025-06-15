package com.guia747.authentication.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.authentication.command.OAuth2AuthenticationCommand;
import com.guia747.authentication.command.RefreshAccessTokenCommand;
import com.guia747.authentication.dto.AuthenticationResponse;
import com.guia747.authentication.dto.OAuth2AuthenticationRequest;
import com.guia747.authentication.dto.RefreshAccessTokenRequest;
import com.guia747.authentication.usecase.OAuth2AuthenticationUseCase;
import com.guia747.authentication.usecase.RefreshAccessTokenUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Base URL for authentication endpoints")
public class OAuth2AuthenticationController {

    private final OAuth2AuthenticationUseCase oauth2AuthenticationUseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

    public OAuth2AuthenticationController(OAuth2AuthenticationUseCase oauth2AuthenticationUseCase,
            RefreshAccessTokenUseCase refreshAccessTokenUseCase) {
        this.oauth2AuthenticationUseCase = oauth2AuthenticationUseCase;
        this.refreshAccessTokenUseCase = refreshAccessTokenUseCase;
    }

    @Operation(
            summary = "Authenticate with OAuth2 authorization code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(
                    schema = @Schema(implementation = AuthenticationResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid input or missing parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content)
    })
    @PostMapping("/oauth2/loginWithGoogle")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
            @Valid @RequestBody OAuth2AuthenticationRequest request, HttpServletRequest httpRequest) {

        OAuth2AuthenticationCommand command = new OAuth2AuthenticationCommand(request.code(),
                httpRequest.getRemoteAddr(),
                httpRequest.getHeader(HttpHeaders.USER_AGENT));

        AuthenticationResponse authenticationResponse = oauth2AuthenticationUseCase.execute(command);
        return ResponseEntity.ok(authenticationResponse);
    }

    @Operation(summary = "Generate a new Access Token using Refresh Token",
            description = "Generates a new Access Token and Refresh Token pair using a valid Refresh Token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully generated new token pair",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token", content = @Content)
    })
    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshAccessTokenRequest request,
            HttpServletRequest httpRequest) {

        RefreshAccessTokenCommand command = new RefreshAccessTokenCommand(request.refreshToken(),
                httpRequest.getRemoteAddr(), httpRequest.getHeader(HttpHeaders.USER_AGENT));

        AuthenticationResponse response = refreshAccessTokenUseCase.execute(command);
        return ResponseEntity.ok(response);
    }
}
