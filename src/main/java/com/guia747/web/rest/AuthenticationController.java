package com.guia747.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guia747.application.dto.SocialAuthenticationResult;
import com.guia747.application.usecase.AuthenticateWithSocialProviderUseCase;
import com.guia747.application.usecase.RefreshAccessTokenUseCase;
import com.guia747.domain.vo.TokenPair;
import com.guia747.infrastructure.security.SecureRefreshTokenCookieService;
import com.guia747.web.dto.AuthenticationResponse;
import com.guia747.web.dto.RefreshTokenResponse;
import com.guia747.web.dto.SocialAuthenticationRequest;
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
    private final SecureRefreshTokenCookieService refreshTokenCookieService;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

    public AuthenticationController(
            AuthenticateWithSocialProviderUseCase authenticateWithSocialProviderUseCase,
            SecureRefreshTokenCookieService refreshTokenCookieService,
            RefreshAccessTokenUseCase refreshAccessTokenUseCase
    ) {
        this.authenticateWithSocialProviderUseCase = authenticateWithSocialProviderUseCase;
        this.refreshTokenCookieService = refreshTokenCookieService;
        this.refreshAccessTokenUseCase = refreshAccessTokenUseCase;
    }

    @Operation(
            summary = "Authenticate with Google",
            description = "Performs user authentication using a Google ID providerToken obtained from a client-side login. " +
                    "This endpoint will either log in an existing user or create a new account if the user is " +
                    "authenticating for the first time. It returns the application's internal JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated an existing user",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully authenticated and created a new user account",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The Authorization header is missing, malformed, or does not contain a Bearer token",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "The provided Google ID providerToken is invalid or expired. " +
                            "The social provider rejected the providerToken.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Google account already linked to another user or account conflict detected",
                    content = @Content
            )
    })
    @PostMapping("/google/authenticate")
    public ResponseEntity<AuthenticationResponse> loginWithGoogle(
            @Valid @RequestBody SocialAuthenticationRequest request,
            HttpServletResponse httpResponse
    ) {
        SocialAuthenticationResult result = authenticateWithSocialProviderUseCase.execute(request.providerToken());

        refreshTokenCookieService.setRefreshTokenCookie(httpResponse, result.tokenPair().refreshToken(),
                result.tokenPair().refreshTokenTtl().toSeconds());

        var response = new AuthenticationResponse(
                result.userId(),
                result.tokenPair().accessToken(),
                result.tokenPair().accessTokenTtl().toSeconds(),
                "Bearer"
        );

        HttpStatus status = result.isNewAccount() ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }

    @Operation(
            summary = "Refresh the access token",
            description = "Refreshes an expired access token using a valid refresh token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully refreshed tokens",
                    content = @Content(
                            schema = @Schema(implementation = RefreshTokenResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired refresh token",
                    content = @Content
            )
    })
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Parameter(description = """
                    Refresh token stored in secure HTTP-only cookie.
                    
                    This cookie is automatically sent by the browser and contains the refresh token
                    needed to generate a new access token.
                    """)
            @CookieValue("_rt") String refreshToken,
            HttpServletResponse httpResponse
    ) {
        TokenPair tokenPair = refreshAccessTokenUseCase.execute(refreshToken);

        refreshTokenCookieService.setRefreshTokenCookie(
                httpResponse,
                tokenPair.refreshToken(),
                tokenPair.refreshTokenTtl().toSeconds()
        );

        RefreshTokenResponse response = new RefreshTokenResponse(
                tokenPair.accessToken(),
                tokenPair.accessTokenTtl().toSeconds(),
                "Bearer"
        );

        return ResponseEntity.ok(response);
    }
}
