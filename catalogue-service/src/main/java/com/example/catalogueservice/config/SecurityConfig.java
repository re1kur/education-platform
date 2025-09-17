package com.example.catalogueservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/api/v1/test",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/api/v1/outbox",
                                "/api/v1/outbox/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/*/pay").authenticated()

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/orders",
                                "/api/v1/catalogue",
                                "/api/v1/categories",
                                "/api/v1/products",
                                "/api/v1/categories/*",
                                "/api/v1/products/*").authenticated()

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/test/token",
                                "/api/v1/users/orders",
                                "/api/v1/users/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/products",
                                "/api/v1/categories",
                                "/api/v1/catalogue").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/catalogue/*",
                                "/api/v1/categories/*",
                                "/api/v1/products/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/catalogue/*",
                                "/api/v1/categories/*",
                                "/api/v1/products/*").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
    }
}
