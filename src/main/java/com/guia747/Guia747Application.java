package com.guia747;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@ConfigurationPropertiesScan(basePackages = "com.guia747.infrastructure.config.properties")
public class Guia747Application {

    public static void main(String[] args) {
        SpringApplication.run(Guia747Application.class, args);
    }
}
