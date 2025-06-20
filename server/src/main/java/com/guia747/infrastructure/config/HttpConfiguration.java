package com.guia747.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
