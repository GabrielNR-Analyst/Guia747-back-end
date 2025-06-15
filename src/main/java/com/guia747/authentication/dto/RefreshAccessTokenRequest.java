package com.guia747.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshAccessTokenRequest(@NotBlank String refreshToken) {

}
