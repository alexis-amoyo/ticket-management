package com.myapp.desk.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Temporarily disabled security configuration
//@Configuration
public class SecurityConfig {

/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/tickets/**").authenticated()
                        .anyRequest().permitAll()
                )
                // Enables browser login (Authorization Code flow)
                .oauth2Login(oauth2 ->
                        oauth2.loginPage("/oauth2/authorization/keycloak")
                )
                // Enables Resource Server (accept Bearer JWT tokens)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("realm_access.roles");
        converter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthConverter;
    }
*/
}




