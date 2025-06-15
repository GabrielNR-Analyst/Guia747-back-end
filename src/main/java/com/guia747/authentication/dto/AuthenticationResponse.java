package com.guia747.authentication.dto;

import java.util.UUID;

public record AuthenticationResponse(
        UUID accountId,
        String accessToken,
        String tokenType,
        long expiresIn
) {

}
