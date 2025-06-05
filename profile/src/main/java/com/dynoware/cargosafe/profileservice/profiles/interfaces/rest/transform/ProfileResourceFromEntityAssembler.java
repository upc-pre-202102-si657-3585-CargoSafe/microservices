package com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.transform;

import com.dynoware.cargosafe.profileservice.profiles.domain.model.aggregates.Profile;
import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.StreetAddress;
import com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.resources.ProfileResource;

/**
 * Assembler to convert a Profile entity to a ProfileResource.
 */
public class ProfileResourceFromEntityAssembler {
    /**
     * Converts a Profile entity to a ProfileResource.
     *
     * @param entity The {@link Profile} entity to convert.
     * @return The {@link ProfileResource} resource.
     */
    public static ProfileResource toResourceFromEntity(Profile entity) {
        StreetAddress streetAddress = entity.getStreetAddress();
        return new ProfileResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmailAddress(),
                streetAddress.street(),
                streetAddress.number(),
                streetAddress.city(),
                streetAddress.postalCode(),
                streetAddress.country()
        );
    }
}

