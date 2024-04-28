package au.edu.cqu.g4.authservice.service.keycloak;

import au.edu.cqu.g4.authservice.dtos.KeycloakAuthResponse;
import au.edu.cqu.g4.authservice.dtos.UserDto;
import au.edu.cqu.g4.authservice.dtos.UserRegistrationDto;

import java.util.Map;

public interface IKeycloakUserService {
    
    UserRegistrationDto createUser(UserRegistrationDto userRegistrationDto);
    KeycloakAuthResponse authorize(String email, String password);
    UserDto getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    void forgotPassword(String email);

}
