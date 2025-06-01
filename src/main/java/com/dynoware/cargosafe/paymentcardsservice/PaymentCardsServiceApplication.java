package com.dynoware.cargosafe.paymentcardsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.dynoware.cargosafe"})
public class PaymentCardsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentCardsServiceApplication.class, args);
    }

}
