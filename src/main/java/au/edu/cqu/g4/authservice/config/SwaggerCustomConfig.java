package au.edu.cqu.g4.authservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerCustomConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                addList("Bearer Authentication"))
                .info(new Info().title("CQU G4 Integration REST API")
                        .description("Spring Integration API.")
                        .version("1.0").contact(new Contact().name("Sangharsha Chaulagain")
                                .email( "www.csangharsha.com.np").url("sangharsha.chaulagain@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

}
