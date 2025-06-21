package com.guia747.web.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request containing a refresh token for token operations")
public record RefreshTokenRequest(
        @Schema(description = "The refresh token for authentication operations",
                example = "3GrloCMBIOGCy7xOIIR6aisWNU7uV4vBH1rF8W-Q_ip-v7sYRM...")
        @NotBlank String refreshToken) {

}
