package au.edu.cqu.g4.authservice.dtos;

import au.edu.cqu.g4.authservice.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
