package com.dynoware.cargosafe.iamservice.kafka.consumer;

import com.dynoware.cargosafe.iamservice.iam.interfaces.acl.IamContextFacade;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
    public void handleCreateUser(Map<String, Object> message) {
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
    }

    @KafkaListener(topics = "iam.get-user-id-by-username", groupId = "iam-service-group")
    public void handleFetchUserIdByUsername(String username) {
        Long userId = iamContextFacade.fetchUserIdByUsername(username);
        System.out.println("User ID for username '" + username + "' is: " + userId);
        kafkaTemplate.send("iam.response.user-id", userId);
    }

    @KafkaListener(topics = "iam.get-username-by-user-id", groupId = "iam-service-group")
    public void handleFetchUsernameByUserId(Long userId) {
        String username = iamContextFacade.fetchUsernameByUserId(userId);
        System.out.println("Username for user ID '" + userId + "' is: " + username);
        kafkaTemplate.send("iam.response.username", username);
    }
}
