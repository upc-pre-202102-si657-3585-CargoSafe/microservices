package com.dynoware.cargosafe.profileservice.profiles.domain.model.queries;

import com.dynoware.cargosafe.profileservice.profiles.domain.model.valueobjects.EmailAddress;

/**
 * Get Profile By Email Query
 */
public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}
