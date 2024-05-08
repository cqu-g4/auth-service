package au.edu.cqu.g4.authservice.dtos;

import au.edu.cqu.g4.authservice.enums.Role;
import lombok.Data;

@Data
public class UserRegistrationDto {
    private String userId;
    private String email;
    private String password;
    private Role role;
}
