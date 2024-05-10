package com.cob.billing.usecases.security.kc;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateKeycloakUserUseCase {
    @Autowired
    Keycloak keycloakService;

    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.billing.client}")
    private String billingClient;

    public void create() {
        RealmResource realmResource = keycloakService.realm(realm);
        ClientRepresentation clientRepresentation = null;
        try {
            clientRepresentation = realmResource.clients().findByClientId(billingClient).get(0);
        } catch (javax.ws.rs.NotAuthorizedException exception) {
            log.warn("admin token is expired");
        }
        UsersResource usersResource = realmResource.users();
    }
}
