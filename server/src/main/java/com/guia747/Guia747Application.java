package com.guia747;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Guia747Application {

    public static void main(String[] args) {
        SpringApplication.run(Guia747Application.class, args);
    }
}
