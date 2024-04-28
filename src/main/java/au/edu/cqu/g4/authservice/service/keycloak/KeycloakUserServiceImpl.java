package au.edu.cqu.g4.authservice.service.keycloak;

import au.edu.cqu.g4.authservice.dtos.UserDto;
import au.edu.cqu.g4.authservice.dtos.UserRegistrationDto;
import au.edu.cqu.g4.authservice.enums.Role;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class KeycloakUserServiceImpl implements IKeycloakUserService {

    private static final String UPDATE_PASSWORD = "UPDATE_PASSWORD";

  //  @Value("${keycloak.realm}")
    private String realm= "admin";

    private Keycloak keycloak;

    public KeycloakUserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public UserRegistrationDto createUser(UserRegistrationDto userRegistrationDto) {

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setUsername(userRegistrationDto.getEmail());
        user.setEmail(userRegistrationDto.getEmail());
        user.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationDto.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);
        log.info("Response |  Status: {} | Status Info: {}", response.getStatus(), response.getStatusInfo());

        if (Objects.equals(201, response.getStatus())) {
            System.out.println("Email"+ userRegistrationDto.getEmail());
            List<UserRepresentation> representationList = usersResource.searchByEmail(userRegistrationDto.getEmail(), true);
            if (!CollectionUtils.isEmpty(representationList)) {
                UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation ->
                        Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                assert userRepresentation1 != null;
                emailVerification(userRepresentation1.getId());
                assignRoleToUser(userRepresentation1.getId(), userRegistrationDto.getRole().getValue());
            }
            return userRegistrationDto;
        }

        throw new RuntimeException("User Not Created. Status: " + response.getStatus());
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserDto getUserById(String userId) {
        UserResource userResource = getUsersResource().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();

        return UserDto.builder()
                .id(userRepresentation.getId())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .role(Role.valueOf(Optional.ofNullable(userRepresentation.getRealmRoles())
                        .flatMap(roles -> roles.stream().findFirst())
                        .orElse("USER")
                        .toUpperCase()))
                .build();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
    }

    @Override
    public void emailVerification(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public void forgotPassword(String email) {
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = usersResource.searchByEmail(email, true).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email not found"));

        UserResource userResource = usersResource.get(userRepresentation.getId());
        List<String> actions = new ArrayList<>();
        actions.add(UPDATE_PASSWORD);
        userResource.executeActionsEmail(actions);
    }

    private RoleRepresentation getRole(String role) {
        RolesResource rolesResource = getRolesResource();
        return rolesResource.get(role).toRepresentation();
    }

    private void assignRoleToUser(String userId, String role) {
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        RoleRepresentation roleRepresentation = getRole(role);
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    private RolesResource getRolesResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.roles();
    }
}
