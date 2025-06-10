package com.guia747.domain.vo;

public record TokenPair(String accessToken, String refreshToken, long accessTokenExpirationInSeconds,
        long refreshTokenExpirationInSeconds
) {

}
