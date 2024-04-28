package au.edu.cqu.g4.authservice.controllers;

import au.edu.cqu.g4.authservice.dtos.KeycloakAuthResponse;
import au.edu.cqu.g4.authservice.dtos.UserDto;
import au.edu.cqu.g4.authservice.dtos.UserLoginDto;
import au.edu.cqu.g4.authservice.dtos.UserRegistrationDto;
import au.edu.cqu.g4.authservice.service.keycloak.IKeycloakUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class KeyCloakUserController {

    private final IKeycloakUserService keycloakUserService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDto> createUser(@RequestBody UserRegistrationDto userRegistrationRecord) {
        return new ResponseEntity<>(keycloakUserService.createUser(userRegistrationRecord), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<KeycloakAuthResponse> login(@RequestBody UserLoginDto userLoginDto) {
        return new ResponseEntity<>(keycloakUserService.authorize(userLoginDto.getEmail(), userLoginDto.getPassword()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return new ResponseEntity<>(keycloakUserService.getUserById(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(keycloakUserService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }

    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }
}
