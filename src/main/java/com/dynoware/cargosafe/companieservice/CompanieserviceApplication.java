package com.dynoware.cargosafe.companieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.dynoware.cargosafe"})

public class CompanieserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanieserviceApplication.class, args);
    }

}
