package com.guia747.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "com.guia747.infrastructure.persistence.jpa.entity")
@EnableJpaRepositories(basePackages = "com.guia747.infrastructure.persistence.jpa.repository")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfiguration {

}
