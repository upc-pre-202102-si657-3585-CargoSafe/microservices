package com.dynoware.cargosafe.profileservice.profiles.interfaces.rest;

import com.dynoware.cargosafe.profileservice.profiles.domain.model.aggregates.Profile;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.commands.CreateProfileCommand;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetAllProfilesQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetProfileByIdQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetProfileByUsernameQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.services.ProfileCommandService;
import com.dynoware.cargosafe.profileservice.profiles.domain.services.ProfileQueryService;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.resources.CreateProfileResource;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.resources.ProfileResource;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.resources.UpdateProfileResource;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.dynoware.cargosafe.profileservice.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.EmailAddress;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.acl.ProfilesContextFacade;

/**
 * ProfilesController
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Available Profile Endpoints")
public class ProfilesController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final ProfilesContextFacade profilesContextFacade;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService, ProfilesContextFacade profilesContextFacade) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
        this.profilesContextFacade = profilesContextFacade;
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create a new profile for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<ProfileResource> createProfile(@PathVariable Long userId, @RequestBody CreateProfileResource resource) {
        try {
            if (resource.email() == null || resource.email().isBlank()) throw new BadRequestException("El email es obligatorio");
            if (profileQueryService.handle(new GetProfileByEmailQuery(new EmailAddress(resource.email()))).isPresent()) {
                throw new ProfileAlreadyExistsException("Ya existe un perfil con el email " + resource.email());
            }
            var id = profilesContextFacade.createProfile(
                resource.firstName(), resource.lastName(), resource.email(), resource.street(), resource.number(), resource.city(), resource.postalCode(), resource.country()
            );
            var profile = profileQueryService.handle(new GetProfileByIdQuery(id));
            if (profile.isEmpty()) throw new ProfileNotFoundException("No se pudo crear el perfil");
            var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(profileResource);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Get a profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) throw new ProfileNotFoundException("No se encontró el perfil con ID " + profileId);
        var profileEntity = profile.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileEntity);
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping
    @Operation(summary = "Get all profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles found"),
            @ApiResponse(responseCode = "404", description = "Profiles not found")})
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var profiles = profileQueryService.handle(new GetAllProfilesQuery());
        var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(profileResources);
    }

    @PutMapping("/{profileId}")
    @Operation(summary = "Update a profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long profileId, @RequestBody UpdateProfileResource resource) {
        var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var profile = profileCommandService.handle(updateProfileCommand);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var updatedProfile = profile.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(updatedProfile);
        return ResponseEntity.ok(profileResource);
    }

    @DeleteMapping("/{profileId}")
    @Operation(summary = "Delete a profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile deleted"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileId) {
        var profileDeleted = profileCommandService.delete(profileId);
        if (!profileDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<ProfileResource> getProfileByUsername(@PathVariable String username) {
        var query = new GetProfileByUsernameQuery(username);
        var profile = profileQueryService.handle(query);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }
}

class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) { super(message); }
}
class ProfileAlreadyExistsException extends RuntimeException {
    public ProfileAlreadyExistsException(String message) { super(message); }
}
class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}

@ControllerAdvice(assignableTypes = ProfilesController.class)
class ProfilesControllerAdvice {
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ProfileNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ProfileAlreadyExistsException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + ex.getMessage());
    }
    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), status.getReasonPhrase(), message));
    }
    static class ErrorResponse {
        public int status;
        public String error;
        public String message;
        public ErrorResponse(int status, String error, String message) {
            this.status = status;
            this.error = error;
            this.message = message;
        }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
    }
}