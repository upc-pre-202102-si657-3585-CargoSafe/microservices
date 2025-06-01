package com.dynoware.cargosafe.paymentcardsservice.profiles.interfaces.rest.transform;

import com.dynoware.cargosafe.paymentcardsservice.profiles.domain.model.commands.UpdateProfileCommand;
import com.dynoware.cargosafe.paymentcardsservice.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    /**
     * Converts an UpdateProfileResource to an UpdateProfileCommand.
     * @param profileId The profile ID
     * @param resource The {@link UpdateProfileResource} resource to convert.
     * @return The {@link UpdateProfileCommand} command.
     */
    public static UpdateProfileCommand toCommandFromResource(Long profileId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                profileId,
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.postalCode(),
                resource.country());
    }
}