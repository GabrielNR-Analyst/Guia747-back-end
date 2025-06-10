package com.guia747.infrastructure.config.properties;

import java.time.Duration;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public record AppSecurityProperties(
        @NestedConfigurationProperty JwtProperties jwt,
        @NestedConfigurationProperty CookieProperties cookie
) {

    public record CookieProperties(Boolean secure) {

    }

    public record JwtProperties(String secret, Duration accessTokenExpiration, Duration refreshTokenExpiration) {

        public JwtProperties {
            if (accessTokenExpiration == null) {
                accessTokenExpiration = Duration.ofHours(1);
            }
            if (refreshTokenExpiration == null) {
                refreshTokenExpiration = Duration.ofDays(30);
            }
        }
    }
}
