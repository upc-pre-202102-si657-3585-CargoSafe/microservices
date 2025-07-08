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

import java.util.List;
import java.util.Optional;

/**
 * ProfilesController
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Available Profile Endpoints")
public class ProfilesController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create a new profile for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<ProfileResource> createProfile(@PathVariable Long userId, @RequestBody CreateProfileResource resource) {
        CreateProfileCommand createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Profile> profile = profileCommandService.handle(createProfileCommand);
        profileCommandService.addUser(resource.email(), userId);
        if (profile.isEmpty()) return ResponseEntity.badRequest().build();
        Profile createdProfile = profile.get();
        ProfileResource profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(createdProfile);
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Get a profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
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
        if (profiles.isEmpty()) return ResponseEntity.notFound().build();
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