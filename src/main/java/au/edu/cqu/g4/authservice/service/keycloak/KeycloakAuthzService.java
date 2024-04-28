package au.edu.cqu.g4.authservice.service.keycloak;

import au.edu.cqu.g4.authservice.config.KeycloakAuth;
import au.edu.cqu.g4.authservice.dtos.KeycloakAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakAuthzService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KeycloakAuth keycloakAuth;


    public KeycloakAuthResponse authorize(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString()); //Optional in case server sends back JSON data

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", keycloakAuth.getClientId());
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("grant_type", "password");

        HttpEntity formEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<KeycloakAuthResponse> response =
                restTemplate.exchange(keycloakAuth.getAuthUrl(), HttpMethod.POST, formEntity, KeycloakAuthResponse.class);
        if(response.getStatusCode() == HttpStatusCode.valueOf(200)) {
            return response.getBody();
        }
        throw new RuntimeException("Not Authorized.");
    }
}
