package com.guia747.infrastructure.security;

import java.time.Instant;

public record JwtToken(String value, Instant expiresAt) {

}
