package com.guia747.infrastructure.security;

import java.time.Duration;

public record JwtToken(String value, Duration ttl) {

}
