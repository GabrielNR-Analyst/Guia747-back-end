package com.guia747.authentication;

import jakarta.validation.constraints.NotBlank;

public record OAuth2AuthenticationRequest(@NotBlank String code) {

}
