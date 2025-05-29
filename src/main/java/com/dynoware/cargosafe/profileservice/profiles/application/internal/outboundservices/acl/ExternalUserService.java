package com.dynoware.cargosafe.profileservice.profiles.application.internal.outboundservices.acl;

import com.dynoware.cargosafe.profileservice.kafka.producer.UserKafkaProducer;
import org.springframework.stereotype.Service;
@Service
public class ExternalUserService {

    private final UserKafkaProducer userKafkaProducer;

    public ExternalUserService(UserKafkaProducer userKafkaProducer) {
        this.userKafkaProducer = userKafkaProducer;
    }

    public void requestUserIdByUsername(String username) {
        userKafkaProducer.sendUserIdRequest(username);
    }

    public void requestUsernameByUserId(Long userId) {
        userKafkaProducer.sendUsernameRequest(userId);
    }
}
