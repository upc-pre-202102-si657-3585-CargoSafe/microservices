package com.dynoware.cargosafe.iamservice.kafka.consumer;

import com.dynoware.cargosafe.iamservice.iam.interfaces.acl.IamContextFacade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IamKafkaConsumer {

    private final IamContextFacade iamContextFacade;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public IamKafkaConsumer(IamContextFacade iamContextFacade, KafkaTemplate<String, Object> kafkaTemplate) {
        this.iamContextFacade = iamContextFacade;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "iam.create-user", groupId = "iam-service-group")
    public void handleCreateUser(ConsumerRecord<String, Map<String, Object>> record, Acknowledgment ack) {
        Map<String, Object> message = record.value();
        try {
            String username = (String) message.get("username");
            String password = (String) message.get("password");
            List<String> roles = (List<String>) message.get("roles");

            Long userId;
            if (roles != null && !roles.isEmpty()) {
                userId = iamContextFacade.createUser(username, password, roles);
            } else {
                userId = iamContextFacade.createUser(username, password);
            }

            System.out.println("Created user ID: " + userId);
            kafkaTemplate.send("iam.response.user-created", userId);

            ack.acknowledge();
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje: " + e.getMessage());
        }
    }


    @KafkaListener(topics = "iam.get-user-id-by-username", groupId = "iam-service-group")
    public void handleFetchUserIdByUsername(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String username = record.value();
        try {
            Long userId = iamContextFacade.fetchUserIdByUsername(username);
            System.out.println("User ID for username '" + username + "' is: " + userId);
            kafkaTemplate.send("iam.response.user-id", userId);

            ack.acknowledge(); 
        } catch (Exception e) {
            System.err.println("Error al obtener el ID del usuario: " + e.getMessage());
        }
    }


    @KafkaListener(topics = "iam.get-username-by-user-id", groupId = "iam-service-group")
    public void handleFetchUsernameByUserId(ConsumerRecord<String, Long> record, Acknowledgment ack) {
        Long userId = record.value();
        try {
            String username = iamContextFacade.fetchUsernameByUserId(userId);
            System.out.println("Username for user ID '" + userId + "' is: " + username);
            kafkaTemplate.send("iam.response.username", username);

            ack.acknowledge(); 
        } catch (Exception e) {
            System.err.println("Error al obtener el nombre de usuario: " + e.getMessage());
            
        }
    }

    @KafkaListener(topics = "iam.user.validation.request", groupId = "iam-service-group")
    public void handleUserValidationRequest(ConsumerRecord<String, Map<String, Object>> record, Acknowledgment ack) {
        Map<String, Object> message = record.value();
        Long userId = null;
        String correlationId = null;
        try {
            Object userIdObj = message.get("userId");
            if (userIdObj instanceof Number n) userId = n.longValue();
            else if (userIdObj instanceof String s) userId = Long.parseLong(s);
            correlationId = (String) message.get("correlationId");
            boolean exists = iamContextFacade.fetchUsernameByUserId(userId) != null && !iamContextFacade.fetchUsernameByUserId(userId).isEmpty();
            Map<String, Object> response = Map.of(
                "userId", userId,
                "exists", exists,
                "correlationId", correlationId
            );
            kafkaTemplate.send("iam.user.validation.response", correlationId, response);
            ack.acknowledge();
        } catch (Exception e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
        }
    }

}
