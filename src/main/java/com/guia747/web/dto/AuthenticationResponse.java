package com.guia747.web.dto;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the successful authentication response containing the application's JWT token.")
public record AuthenticationResponse(
        @Schema(
                description = "The unique identifier for the user account.",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID accountId,

        @Schema(
                description = "The JWT Access Token for authorizing subsequent requests to the API",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWI..."
        )
        String accessToken,

        @Schema(description = "The lifetime of the access token in seconds.", example = "3600")
        long expiresIn,

        @Schema(description = "The type of the token.", example = "Bearer")
        String tokenType) {

}
