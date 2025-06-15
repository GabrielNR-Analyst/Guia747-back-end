package com.guia747.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties("guia747.security.oauth2.google")
public class OAuth2ProviderConfiguration {

    private String clientId;
    private String clientSecret;

    private String userInfoUri;
    private String tokenUri;
}
