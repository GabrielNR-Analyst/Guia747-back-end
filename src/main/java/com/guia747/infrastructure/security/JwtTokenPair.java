package com.guia747.infrastructure.security;

import java.time.Duration;

public record JwtTokenPair(String accessToken, String refreshToken, Duration accessTokenTtl, Duration refreshTokenTtl) {

}
