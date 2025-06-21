package com.guia747.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.authentication.command.AuthenticateWithOAuth2Command;
import com.guia747.application.authentication.command.RefreshAccessTokenCommand;
import com.guia747.web.dtos.authentication.AuthenticationResponse;
import com.guia747.web.dtos.authentication.OAuth2AuthenticationRequest;
import com.guia747.web.dtos.authentication.RefreshTokenRequest;
import com.guia747.application.authentication.usecase.AuthenticateWithOAuth2UseCase;
import com.guia747.application.authentication.usecase.RefreshAccessTokenUseCase;
import com.guia747.application.authentication.usecase.RevokeRefreshTokenUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Base URL for authentication endpoints")
public class AuthenticationController {

    private final AuthenticateWithOAuth2UseCase authenticateWithOAuth2UseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;
    private final RevokeRefreshTokenUseCase revokeRefreshTokenUseCase;

    public AuthenticationController(
            AuthenticateWithOAuth2UseCase authenticateWithOAuth2UseCase,
            RefreshAccessTokenUseCase refreshAccessTokenUseCase,
            RevokeRefreshTokenUseCase revokeRefreshTokenUseCase
    ) {
        this.authenticateWithOAuth2UseCase = authenticateWithOAuth2UseCase;
        this.refreshAccessTokenUseCase = refreshAccessTokenUseCase;
        this.revokeRefreshTokenUseCase = revokeRefreshTokenUseCase;
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
            @Valid @RequestBody OAuth2AuthenticationRequest request,
            HttpServletRequest httpRequest
    ) {

        AuthenticateWithOAuth2Command command = new AuthenticateWithOAuth2Command(request.code(),
                httpRequest.getRemoteAddr(),
                httpRequest.getHeader(HttpHeaders.USER_AGENT));

        AuthenticationResponse authenticationResponse = authenticateWithOAuth2UseCase.execute(command);
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
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request,
            HttpServletRequest httpRequest
    ) {

        RefreshAccessTokenCommand command = new RefreshAccessTokenCommand(request.refreshToken(),
                httpRequest.getRemoteAddr(), httpRequest.getHeader(HttpHeaders.USER_AGENT));

        AuthenticationResponse response = refreshAccessTokenUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Logout and revoke refresh token",
            description = "Revokes the provided refresh token, effectively logging out the user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout successful, refresh token revoked"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        revokeRefreshTokenUseCase.execute(request.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
