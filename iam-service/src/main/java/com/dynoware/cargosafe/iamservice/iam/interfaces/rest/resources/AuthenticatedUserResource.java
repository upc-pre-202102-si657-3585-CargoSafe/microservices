package  com.dynoware.cargosafe.iamservice.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String token, String role) {
}
