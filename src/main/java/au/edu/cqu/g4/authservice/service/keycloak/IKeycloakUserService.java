package au.edu.cqu.g4.authservice.service.keycloak;

import au.edu.cqu.g4.authservice.dtos.UserDto;
import au.edu.cqu.g4.authservice.dtos.UserRegistrationDto;

public interface IKeycloakUserService {
    
    UserRegistrationDto createUser(UserRegistrationDto userRegistrationDto);
    UserDto getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    void forgotPassword(String email);

}
