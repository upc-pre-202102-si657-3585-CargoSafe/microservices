package com.dynoware.cargosafe.tripsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TripsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripsServiceApplication.class, args);
	}

}
