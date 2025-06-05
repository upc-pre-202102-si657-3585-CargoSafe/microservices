package  com.dynoware.cargosafe.iamservice.iam.interfaces.acl;



import java.util.List;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */


import java.util.List;

/**
 * IamContextFacade
 * <p>
 *     This interface provides a facade to the IAM context.
 *     It is used to interact with the IAM context.
 *     It provides methods to create a user, fetch a user by username, fetch a username by user id.
 * </p>
 */
public interface IamContextFacade {
    /**
     * Creates a user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user id of the created user.
     */
    Long createUser(String username, String password);
    /**
     * Creates a user with the given username, password and roles.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param roles The roles of the user.
     * @return The user id of the created user.
     */
    Long createUser(String username, String password, List<String> roles);
    /**
     * Fetches the user id of the user with the given username.
     * @param username The username of the user.
     * @return The user id of the user.
     */
    Long fetchUserIdByUsername(String username);
    /**
     * Fetches the username of the user with the given user id.
     * @param userId The user id of the user.
     * @return The username of the user.
     */
    String fetchUsernameByUserId(Long userId);
}