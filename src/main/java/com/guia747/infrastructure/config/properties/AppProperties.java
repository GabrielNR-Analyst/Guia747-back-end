package com.guia747.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        @NestedConfigurationProperty AppSecurityProperties security
) {

}
