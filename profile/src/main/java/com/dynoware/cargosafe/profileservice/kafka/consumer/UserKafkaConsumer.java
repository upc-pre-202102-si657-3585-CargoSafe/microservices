package com.dynoware.cargosafe.profileservice.kafka.consumer;

import com.dynoware.cargosafe.profileservice.kafka.messages.UserIdResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserKafkaConsumer {

    private final Map<String, CompletableFuture<Long>> pendingUserIdRequests = new ConcurrentHashMap<>();
    private final Map<String, Object> usernameCache = new ConcurrentHashMap<>();
    private static final Object PENDING_MARKER = new Object(); // Usamos un objeto como marcador

    private Long latestUserId;
    private String latestUsername;

    /**
     * Registra una nueva solicitud de userId por username.
     */

    public CompletableFuture<Long> registerUserIdRequest(String username) {
        if (username == null) {
            throw new IllegalArgumentException("El username no puede ser null");
        }

        // Marca el username como pendiente con un marcador especial
        usernameCache.put(username, PENDING_MARKER);

        CompletableFuture<Long> future = new CompletableFuture<>();
        pendingUserIdRequests.put(username, future);
        System.out.println("ℹ Solicitud registrada para el username: " + username);

        return future;
    }

    /**
     * Listener para respuestas del userId desde IAM.
     */
    @KafkaListener(topics = "iam.response.user-id", groupId = "profileservice-group")
    public void receiveUserId(ConsumerRecord<String, Long> record) {
        Long userId = record.value();  // Aquí obtenemos el Long que es el userId
        String username = (record.key() != null) ? record.key() : usernameCache.entrySet().stream()
                .filter(entry -> entry.getValue() == null)  // Encuentra el username con userId pendiente
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null); // Si no encuentra el username pendiente, asigna null (manejar este caso)
        System.out.println("ℹ Recibido userId para username '" + username + "': " + userId);

        if (username != null) {
            System.out.println("ℹ Recibido userId para username '" + username + "': " + userId);

            CompletableFuture<Long> future = pendingUserIdRequests.remove(username);
            if (future != null) {
                future.complete(userId);  // Completa la solicitud pendiente con el userId recibido
                usernameCache.put(username, userId.toString()); // Marca el username como resuelto
            } else {
                System.err.println("No se encontró solicitud pendiente para el username: " + username);
            }
        } else {
            System.err.println("No se pudo asociar el userId con un username válido");
        }
    }
    /**
     * Listener opcional para otros eventos del tipo username.
     */
    @KafkaListener(topics = "iam.response.username", groupId = "profileservice-group")
    public void receiveUsername(ConsumerRecord<String, String> record) {
        latestUsername = record.value();
        System.out.println("ℹ Recibido username: " + latestUsername);
    }

    public Optional<Long> getLatestUserId() {
        return Optional.ofNullable(latestUserId);
    }

    public Optional<String> getLatestUsername() {
        return Optional.ofNullable(latestUsername);
    }
}
