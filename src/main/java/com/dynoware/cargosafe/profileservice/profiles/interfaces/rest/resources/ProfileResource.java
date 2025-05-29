package com.dynoware.cargosafe.profileservice.profiles.interfaces.rest.resources;

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
