package com.guia747.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.credentials")
public record GoogleProperties(String clientId) {

}
