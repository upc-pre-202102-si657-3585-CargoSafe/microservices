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
        // Envía una solicitud a IAM para obtener el userId basado en el username
        userKafkaProducer.sendUserIdRequest(query.username());

        // Espera la respuesta del consumidor
        Optional<Long> optionalUserId = userKafkaConsumer.getLatestUserId();
        if (optionalUserId.isEmpty()) {
            throw new IllegalStateException("No se pudo obtener el userId desde IAM");
        }

        Long userId = optionalUserId.get();

        // Busca el Profile usando el userId
        return profileRepository.findByUserId(userId);
    }

}
