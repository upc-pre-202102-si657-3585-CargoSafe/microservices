package com.dynoware.cargosafe.profileservice.profiles.application.internal.queryservices;

import com.dynoware.cargosafe.profileservice.kafka.consumer.UserKafkaConsumer;
import com.dynoware.cargosafe.profileservice.kafka.producer.UserKafkaProducer;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.aggregates.Profile;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetAllProfilesQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetProfileByIdQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetProfileByUsernameQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.services.ProfileQueryService;
import com.dynoware.cargosafe.profileservice.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Profile Query Service Implementation
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;
    private final UserKafkaProducer userKafkaProducer;
    private final UserKafkaConsumer userKafkaConsumer;

    /**
     * Constructor
     *
     * @param profileRepository The {@link ProfileRepository} instance
     */
    public ProfileQueryServiceImpl(ProfileRepository profileRepository, UserKafkaProducer userKafkaProducer, UserKafkaConsumer userKafkaConsumer) {
        this.profileRepository = profileRepository;
        this.userKafkaProducer = userKafkaProducer;
        this.userKafkaConsumer = userKafkaConsumer;
    }


    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }


    @Override
    public Optional<Profile> handle(GetProfileByEmailQuery query) {
        return profileRepository.findByEmailAddress(query.emailAddress());
    }




    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }


    @Override
    public Optional<Profile> handle(GetProfileByUsernameQuery query) {
        String username = query.username();

        try {
            // Registrar la solicitud esperando la respuesta
            var future = userKafkaConsumer.registerUserIdRequest(username);

            // Enviar la solicitud a IAM
            userKafkaProducer.sendUserIdRequest(username);

            // Esperar respuesta hasta 5 segundos
            Long userId = future.get(20, java.util.concurrent.TimeUnit.SECONDS);

            // Buscar el perfil por el userId obtenido
            return profileRepository.findByUserId(userId);

        } catch (TimeoutException e) {
            // Log para el caso de timeout
            System.err.println("ℹ Error: Timeout al esperar la respuesta para el username: " + username);
            throw new IllegalStateException("No se pudo obtener el userId desde IAM debido a un timeout", e);
        } catch (InterruptedException e) {
            // Log para el caso de interrupción del hilo
            System.err.println("ℹ Error: La operación fue interrumpida mientras esperaba la respuesta para el username: " + username);
            Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
            throw new IllegalStateException("Operación interrumpida mientras se esperaba el userId", e);
        } catch (ExecutionException e) {
            // Log para el caso de error durante la ejecución del futuro
            System.err.println("ℹ Error: Hubo un problema al ejecutar la solicitud para el username: " + username);
            throw new IllegalStateException("Error al ejecutar la solicitud para obtener el userId desde IAM", e);
        } catch (Exception e) {
            // Log para cualquier otro tipo de excepción
            System.err.println("ℹ Error inesperado al manejar la consulta para el username: " + username);
            e.printStackTrace(); // Mostrar detalles de la excepción
            throw new IllegalStateException("No se pudo obtener el userId desde IAM debido a un error inesperado", e);
        }
    }



}
