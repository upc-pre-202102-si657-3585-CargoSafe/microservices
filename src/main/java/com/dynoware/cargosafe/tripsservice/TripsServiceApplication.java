package com.dynoware.cargosafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TripsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripsServiceApplication.class, args);
    }
}