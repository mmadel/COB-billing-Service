package com.cob.billing.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
    @Value("${kc.url}")
    private String baseUrl;
    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.client.id}")
    private String clientId;
    @Value("${kc.client.secret}")
    private String clientSecret;

    @Value("${kc.administrator.username}")
    private String userName;
    @Value("${kc.administrator.password}")
    private String password;
    @Bean()
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(baseUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.PASSWORD)
                .username(userName)
                .password(password)
                .build();
    }
}
