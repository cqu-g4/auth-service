package au.edu.cqu.g4.authservice.controllers;

import au.edu.cqu.g4.authservice.service.keycloak.IKeycloakUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {

    private final IKeycloakUserService keycloakUserService;

    @PutMapping("/{username}/forgot-password")
    public void forgotPassword(@PathVariable String username) {
        keycloakUserService.forgotPassword(username);
    }

}
