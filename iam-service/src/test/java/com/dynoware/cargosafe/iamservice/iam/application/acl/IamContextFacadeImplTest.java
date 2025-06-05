package com.dynoware.cargosafe.iamservice.iam.application.acl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;

import com.dynoware.cargosafe.iamservice.iam.application.internal.queryservices.RoleQueryServiceImpl;
import com.dynoware.cargosafe.iamservice.iam.domain.model.aggregates.User;
import com.dynoware.cargosafe.iamservice.iam.domain.model.commands.SignUpCommand;
import com.dynoware.cargosafe.iamservice.iam.domain.model.entities.Role;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetRoleByNameQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetUserByIdQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetUserByUsernameQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.valueobjects.Roles;
import com.dynoware.cargosafe.iamservice.iam.domain.services.RoleQueryService;
import com.dynoware.cargosafe.iamservice.iam.domain.services.UserCommandService;
import com.dynoware.cargosafe.iamservice.iam.domain.services.UserQueryService;
import io.jsonwebtoken.lang.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class IamContextFacadeImplTest {

    @Mock
    private UserCommandService userCommandService;
    @Mock
    private RoleQueryServiceImpl roleQueryService;
    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private IamContextFacadeImpl iamContextFacade;

    private String username;
    private String password;

    @BeforeEach
    void setUp() {
        username = "testUser";
        password = "testPassword";
    }
    @Test
    void createUser_shouldReturnUserId_whenCreationIsSuccessful() {
        // Arrange
        Role role = new Role(Roles.ROLE_USER);
        var signUpCommand = new SignUpCommand(username, password, List.of(role));
        User user = new User(username, password, List.of(role));

        // Crear un ArgumentCaptor para capturar el argumento pasado a handle
        ArgumentCaptor<GetRoleByNameQuery> captor = ArgumentCaptor.forClass(GetRoleByNameQuery.class);

        // Configura el mock para que devuelva un Optional con el rol
        when(roleQueryService.handle(captor.capture()))
                .thenReturn(Optional.of(role));  // Asegura que el mock devuelve el rol

        // Configura el mock para manejar el comando de registro
        when(userCommandService.handle(signUpCommand)).thenReturn(Optional.of(user));

        // Act
        Long userId = iamContextFacade.createUser(username, password);

        // Assert
        assertThat(userId).isEqualTo(user.getId());

        // Verifica que el argumento capturado sea el esperado
        assertThat(captor.getValue().name()).isEqualTo(Roles.ROLE_USER);
    }

    @Test
    void createUser_shouldReturnZero_whenCreationFails() {
        // Arrange
        var signUpCommand = new SignUpCommand(username, password, List.of(new Role(Roles.ROLE_USER)));
        when(userCommandService.handle(signUpCommand)).thenReturn(Optional.empty());
        // Crear un ArgumentCaptor para capturar el argumento pasado a handle
        ArgumentCaptor<GetRoleByNameQuery> captor = ArgumentCaptor.forClass(GetRoleByNameQuery.class);


        // Act
        Long userId = iamContextFacade.createUser(username, password);

        // Assert
        assertThat(userId).isEqualTo(0L);
    }

    @Test
    void fetchUserIdByUsername_shouldReturnUserId_whenUserExists() {
        // Arrange
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        User user = new User(username, password);
        when(userQueryService.handle(getUserByUsernameQuery)).thenReturn(Optional.of(user));

        // Act
        Long userId = iamContextFacade.fetchUserIdByUsername(username);

        // Assert
        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    void fetchUserIdByUsername_shouldReturnZero_whenUserDoesNotExist() {
        // Arrange
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        when(userQueryService.handle(getUserByUsernameQuery)).thenReturn(Optional.empty());

        // Act
        Long userId = iamContextFacade.fetchUserIdByUsername(username);

        // Assert
        assertThat(userId).isEqualTo(0L);
    }

    @Test
    void fetchUsernameByUserId_shouldReturnUsername_whenUserExists() {
        // Arrange
        Long userId = 1L;
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        User user = new User(username, password);
        when(userQueryService.handle(getUserByIdQuery)).thenReturn(Optional.of(user));

        // Act
        String fetchedUsername = iamContextFacade.fetchUsernameByUserId(userId);

        // Assert
        assertThat(fetchedUsername).isEqualTo(username);
    }

    @Test
    void fetchUsernameByUserId_shouldReturnEmptyString_whenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        when(userQueryService.handle(getUserByIdQuery)).thenReturn(Optional.empty());

        // Act
        String fetchedUsername = iamContextFacade.fetchUsernameByUserId(userId);

        // Assert
        assertThat(fetchedUsername).isEqualTo(Strings.EMPTY);
    }
}
