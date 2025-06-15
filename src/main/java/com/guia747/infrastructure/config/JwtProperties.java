package com.guia747.infrastructure.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "guia747.security.jwt")
public class JwtProperties {

    private String secret;
    private Duration accessTokenTtl;
    private Duration refreshTokenTtl;
}
