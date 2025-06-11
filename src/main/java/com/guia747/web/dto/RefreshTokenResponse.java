package com.guia747.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshTokenResponse(
        @Schema(
                description = "The JWT Access Token for authorizing subsequent requests to the API",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWI..."
        )
        String accessToken,

        @Schema(description = "The lifetime of the access token in seconds.", example = "3600")
        long expiresIn,

        @Schema(description = "The type of the token.", example = "Bearer")
        String tokenType
) {

}
