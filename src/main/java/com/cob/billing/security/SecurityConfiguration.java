package com.cob.billing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
                new DelegatingJwtGrantedAuthoritiesConverter(
                        new JwtGrantedAuthoritiesConverter(),
                        new KeycloakJwtRolesConverter());
        httpSecurity.oauth2ResourceServer().jwt().jwtAuthenticationConverter(
                jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt)));
        httpSecurity.authorizeRequests()
                .antMatchers("/init/**").permitAll()
                .anyRequest().authenticated();
        return httpSecurity.build();
    }
}
