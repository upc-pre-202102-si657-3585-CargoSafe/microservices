package com.dynoware.cargosafe.iamservice.iam.application.acl;

import com.dynoware.cargosafe.iamservice.iam.application.internal.queryservices.RoleQueryServiceImpl;
import com.dynoware.cargosafe.iamservice.iam.domain.model.commands.SignUpCommand;
import com.dynoware.cargosafe.iamservice.iam.domain.model.entities.Role;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetRoleByNameQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetUserByIdQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetUserByUsernameQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.valueobjects.Roles;
import com.dynoware.cargosafe.iamservice.iam.domain.services.UserCommandService;
import com.dynoware.cargosafe.iamservice.iam.domain.services.UserQueryService;
import com.dynoware.cargosafe.iamservice.iam.interfaces.acl.IamContextFacade;
import com.dynoware.cargosafe.iamservice.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IamContextFacadeImpl
 * <p>
 *     This class provides a facade to the IAM context.
 *     It is used to interact with the IAM context.
 *     It provides methods to create a user, fetch a user by username, fetch a username by user id.
 * </p>
 */
@Service
public class IamContextFacadeImpl implements IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final RoleQueryServiceImpl roleQueryService;
    /**
     * Constructor
     * @param userCommandService The user command service.
     * @param userQueryService The user query service.
     */
    public IamContextFacadeImpl(UserCommandService userCommandService, UserQueryService userQueryService, RoleQueryServiceImpl roleQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.roleQueryService = roleQueryService;
    }

    // inherited javadoc
    @Override
    public Long createUser(String username, String password) {
        Role roles = roleQueryService.handle(new GetRoleByNameQuery(Roles.ROLE_USER))
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        var signUpCommand = new SignUpCommand(username, password, List.of(roles));

        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    @Override
    public Long createUser(String username, String password, List<String> roleNames) {
        // Convierte cada nombre de rol de tipo String en el enum Roles
        List<Role> roles = roleNames.stream()
                .map(Role::toRoleFromName)  // Convertir String a Role
                .toList();

        // Aquí puedes verificar si los roles existen en la base de datos
        List<Role> rolesFromDatabase = roles.stream()
                .map(role -> roleQueryService.handle(new GetRoleByNameQuery(role.getName())) // Asumiendo que el servicio maneja el nombre
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role.getName())))
                .collect(Collectors.toList());

        // Crear el comando de registro con los roles encontrados
        var signUpCommand = new SignUpCommand(username, password, rolesFromDatabase);

        // Llamar al servicio para crear el usuario
        var result = userCommandService.handle(signUpCommand);

        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    // inherited javadoc
    @Override
    public Long fetchUserIdByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var result = userQueryService.handle(getUserByUsernameQuery);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    // inherited javadoc
    @Override
    public String fetchUsernameByUserId(Long userId) {
        var getUserByUserIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByUserIdQuery);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getUsername();
    }
}