package com.guia747.web.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for OAuth2 authentication")
public record OAuth2AuthenticationRequest(
        @Schema(description = "The authorization code received from the OAuth2 provider")
        @NotBlank String code
) {

}
