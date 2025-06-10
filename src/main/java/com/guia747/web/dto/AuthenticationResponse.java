package com.guia747.web.dto;

import java.util.UUID;

public record AuthenticationResponse(UUID userId, String accessToken, long expiresIn, String tokenType) {

}
