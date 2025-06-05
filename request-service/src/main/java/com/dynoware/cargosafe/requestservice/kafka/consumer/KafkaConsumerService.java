package com.dynoware.cargosafe.requestservice.kafka.consumer;

import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "request-service-created", groupId = "request-service-group")
    public void listen(RequestService requestService) {
        System.out.println("Evento recibido de Kafka: " + requestService);
    }
}
