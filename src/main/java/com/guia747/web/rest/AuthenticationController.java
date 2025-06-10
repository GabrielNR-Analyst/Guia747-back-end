package com.guia747.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.dto.SocialAuthenticationResult;
import com.guia747.application.usecase.AuthenticateWithSocialProviderUseCase;
import com.guia747.web.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {

    private final AuthenticateWithSocialProviderUseCase authenticateWithSocialProviderUseCase;

    public AuthenticationController(AuthenticateWithSocialProviderUseCase authenticateWithSocialProviderUseCase) {
        this.authenticateWithSocialProviderUseCase = authenticateWithSocialProviderUseCase;
    }

    @Operation(
            summary = "Authenticate with Google",
            description = "Performs user authentication using a Google ID token obtained from a client-side login. " +
                    "This endpoint will either log in an existing user or create a new account if the user is " +
                    "authenticating for the first time. It returns the application's internal JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated an existing user.",
                    content = @Content(schema = @Schema(
                            implementation = AuthenticationResponse.class,
                            contentMediaType = MediaType.APPLICATION_JSON_VALUE
                    ))
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully authenticated and created a new user account.",
                    content = @Content(schema = @Schema(
                            implementation = AuthenticationResponse.class,
                            contentMediaType = MediaType.APPLICATION_JSON_VALUE
                    ))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The Authorization header is missing, malformed, or does not contain a Bearer token",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "The provided Google ID token is invalid or expired. " +
                            "The social provider rejected the token.",
                    content = @Content
            )
    })
    @PostMapping("/google/authenticate")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
            @Parameter(
                    description = "The Google ID token, prefixed with 'Bearer '.",
                    required = true,
                    example = "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjY..."
            )
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            HttpServletResponse httpResponse
    ) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        SocialAuthenticationResult result = authenticateWithSocialProviderUseCase.execute(accessToken);

        var response = new AuthenticationResponse(
                result.userId(),
                result.tokenPair().accessToken(),
                result.tokenPair().accessTokenExpirationInSeconds(),
                "Bearer"
        );

        HttpStatus status = result.isNewAccount() ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }
}
