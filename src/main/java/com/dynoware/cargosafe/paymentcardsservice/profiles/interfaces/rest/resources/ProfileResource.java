package com.dynoware.cargosafe.paymentcardsservice.profiles.interfaces.rest.resources;

/**
 * Resource for a profile.
 */
public record ProfileResource(
        Long id,
        String fullName,
        String email,
        String street,
        String number,
        String city,
        String postalCode,
        String country) {
}
