package com.dynoware.cargosafe.iamservice.controller;

import com.dynoware.cargosafe.iamservice.kafka.producer.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    private final KafkaProducerService producerService;

    public KafkaTestController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public String send(@RequestParam String message) {
        producerService.send("test-topic", message);
        return "Mensaje enviado!";
    }
}
