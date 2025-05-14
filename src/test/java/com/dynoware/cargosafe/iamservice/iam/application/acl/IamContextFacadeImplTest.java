package com.dynoware.cargosafe.iamservice.iam.application.acl;

import static org.junit.jupiter.api.Assertions.*;

import com.dynoware.cargosafe.iamservice.iam.domain.model.aggregates.User;
import com.dynoware.cargosafe.iamservice.iam.domain.model.commands.SignUpCommand;
import com.dynoware.cargosafe.iamservice.iam.domain.model.entities.Role;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetUserByIdQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetUserByUsernameQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.valueobjects.Roles;
import com.dynoware.cargosafe.iamservice.iam.domain.services.UserCommandService;
import com.dynoware.cargosafe.iamservice.iam.domain.services.UserQueryService;
import io.jsonwebtoken.lang.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IamContextFacadeImplTest {

    @Mock
    private UserCommandService userCommandService;

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
        var signUpCommand = new SignUpCommand(username, password, List.of(new Role(Roles.ROLE_USER)));
        User user = new User(username, password, List.of(new Role(Roles.ROLE_USER)));
        when(userCommandService.handle(signUpCommand)).thenReturn(Optional.of(user));

        // Act
        Long userId = iamContextFacade.createUser(username, password);

        // Assert
        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    void createUser_shouldReturnZero_whenCreationFails() {
        // Arrange
        var signUpCommand = new SignUpCommand(username, password, List.of(new Role(Roles.ROLE_USER)));
        when(userCommandService.handle(signUpCommand)).thenReturn(Optional.empty());

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
