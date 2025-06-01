package com.dynoware.cargosafe.paymentcardsservice.profiles.domain.model.queries;

import com.dynoware.cargosafe.paymentcardsservice.profiles.domain.model.valueobjects.EmailAddress;

/**
 * Get Profile By Email Query
 */
public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}
