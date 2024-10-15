package com.cob.billing.usecases.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DisableUserUseCase {
    @Autowired
    Keycloak keycloakService;
    @Value("${kc.realm}")
    private String realm;

    public void disable(String uuid) {
        RealmResource realmResource = keycloakService.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserRepresentation user = usersResource.get(uuid).toRepresentation();
        user.setEnabled(false);
        usersResource.get(uuid).update(user);

    }
}
