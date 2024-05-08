package au.edu.cqu.g4.authservice.service.keycloak;

import au.edu.cqu.g4.authservice.dtos.UserDto;
import au.edu.cqu.g4.authservice.dtos.UserRegistrationDto;
import au.edu.cqu.g4.authservice.enums.Role;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class KeycloakUserServiceImplTest {

    @Mock
    private Keycloak keycloak;

    @Mock
    private RealmResource realmResource;

    @Mock
    private UsersResource usersResource;

    @Mock
    private UserResource userResource;

    @InjectMocks
    private KeycloakUserServiceImpl keycloakUserService;

  @BeforeEach
    void setUp() {
        // Using lenient to avoid strict stubbing errors
        lenient().when(keycloak.realm(anyString())).thenReturn(realmResource);
        lenient().when(realmResource.users()).thenReturn(usersResource);
        lenient().when(usersResource.get(anyString())).thenReturn(userResource);
        lenient().when(userResource.toRepresentation()).thenReturn(new UserRepresentation());
    }

    @Disabled
    @Test
    void createUser_shouldReturnUserRegistrationDto_whenUserIsCreatedSuccessfully() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId("admin-cli");
        Response response = Response.status(Response.Status.CREATED).build();
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(usersResource.searchByEmail(anyString(), eq(true))).thenReturn(java.util.List.of(userRepresentation));

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("test@example.com");
//        userRegistrationDto.setFirstName("test");
//        userRegistrationDto.setLastName("test");
        userRegistrationDto.setPassword("test");
        userRegistrationDto.setRole(Role.USER);

        UserRegistrationDto result = keycloakUserService.createUser(userRegistrationDto);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Disabled
    @Test
    void getUserById_shouldReturnUserDto_whenUserExists() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId("admin-cli");
        userRepresentation.setFirstName("test");
        userRepresentation.setLastName("test");
        userRepresentation.setEmail("test@example.com");
        when(userResource.toRepresentation()).thenReturn(userRepresentation);

        UserDto result = keycloakUserService.getUserById("admin-cli");

        assertNotNull(result);
        assertEquals("admin-cli", result.getId());
        System.out.println("Success");
    }


}
