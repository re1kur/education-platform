package com.example.taskservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "Keycloak Auth",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        bearerFormat = "JWT",
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        tokenUrl = "${spring.security.oauth2.client.provider.keycloak.issuer-uri}/protocol/openid-connect/token",
                        authorizationUrl = "${spring.security.oauth2.client.provider.keycloak.issuer-uri}/protocol/openid-connect/auth",
                        scopes = {
                                @OAuthScope(name = "openid")
                        }
                )
        )
)
@OpenAPIDefinition(
        info = @Info(title = "Task Service API", version = "v1"),
        security = {
                @SecurityRequirement(name = "Keycloak Auth")
        }
)
@Configuration
public class SwaggerConfig {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI().components(new Components()
//                .addSecuritySchemes("keycloak auth", SecurityScheme.));
//    }
}
