package au.edu.cqu.g4.authservice.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeycloakAuth {
    private String authUrl;
    private String clientId;
}
