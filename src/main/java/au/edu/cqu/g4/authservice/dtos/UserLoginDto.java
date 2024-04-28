package au.edu.cqu.g4.authservice.dtos;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
}
