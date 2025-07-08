package com.dynoware.cargosafe.profileservice.profiles.application.internal.commandservices;

import com.dynoware.cargosafe.profileservice.profiles.domain.model.aggregates.Profile;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.commands.CreateProfileCommand;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.commands.UpdateProfileCommand;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.EmailAddress;
import com.dynoware.cargosafe.profileservice.profiles.domain.services.ProfileCommandService;
import com.dynoware.cargosafe.profileservice.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Profile Command Service Implementation
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        var emailAddress = new EmailAddress(command.email());
        if (profileRepository.existsByEmailAddress(emailAddress)) {
            throw new IllegalArgumentException("Profile with email address already exists");
        }
        var profile = new Profile(command);

        profileRepository.save(profile);
        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {
        var profileOptional = profileRepository.findById(command.profileId());
        if (profileOptional.isEmpty()) {
            return Optional.empty();
        }
        var profile = profileOptional.get();
        profile.updateProfile(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.street(),
                command.number(),
                command.city(),
                command.postalCode(),
                command.country()
        );
        profileRepository.save(profile);
        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> addUser(String email, Long userId) {
        // Verificamos si el userId no es null
        var emailAddress = new EmailAddress(email);

        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede ser nulo.");
        }

        // Buscamos el perfil existente por username
        Optional<Profile> existingProfile = profileRepository.findByEmailAddress(emailAddress);

        if (existingProfile.isEmpty()) {
            // Si no existe el perfil, retornamos un Optional vacío o lanzamos una excepción
            return Optional.empty();
        }

        // Si existe el perfil, lo actualizamos con el userId proporcionado
        Profile profile = existingProfile.get();
        profile.setUser(userId);  // Asignamos el userId

        // Guardamos el perfil actualizado en la base de datos
        Profile updatedProfile = profileRepository.save(profile);

        return Optional.of(updatedProfile);
    }

    @Override
    public boolean delete(Long profileId) {
        if (profileRepository.existsById(profileId)) {
            profileRepository.deleteById(profileId);
            return true;
        }
        return false;
    }
}