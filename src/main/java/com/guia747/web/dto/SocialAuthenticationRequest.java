package com.guia747.web.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record SocialAuthenticationRequest(
        @Schema(
                description = "Token received from the social provider.",
                example = "eyJhbGciOiJSUzI1NiIsImtpZCI6..."
        )
        @NotBlank String providerToken) {

}
