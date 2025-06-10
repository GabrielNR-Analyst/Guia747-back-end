package com.guia747.web.dto;

import java.util.UUID;

public record GoogleAuthenticationResponse(UUID userId, boolean isNewUser) {

}
