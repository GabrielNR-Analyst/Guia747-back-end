package com.guia747.web.dtos.authentication;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body after successful authentication")
public record AuthenticationResponse(
        @Schema(
                description = "Unique identifier of authenticated user account",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID accountId,

        @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Access token time to live in seconds", example = "3600")
        long expiresIn,

        @Schema(
                description = "Refresh Token used to obtain new access tokens",
                example = "3GrloCMBIOGCy7xOIIR6aisWNU7uV4vBH1rF8W-Q_ip-v7sYRM..."
        )
        String refreshToken,

        @Schema(description = "Refresh token time to live in seconds", example = "2592000")
        long refreshTokenExpiresIn,

        @Schema(description = "Type of the token, always 'Bearer'", example = "Bearer")
        String tokenType
) {

}
