package com.cob.billing.usecases.security.kc;

import com.cob.billing.model.security.RoleScope;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignUserRolesUseCase {
    @Value("${kc.billing.client}")
    private String billingResource;

    public void assign(String uuid, List<RoleScope> roles, RealmResource realmResource) {

        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(billingResource).get(0);
        List<RoleRepresentation> roleRepresentations = getRolesRepresentation(roles, realmResource, clientRepresentation);
        getUserResource(uuid, realmResource).roles().clientLevel(clientRepresentation.getId()).add(roleRepresentations);
    }

    private List<RoleRepresentation> getRolesRepresentation(List<RoleScope> roles, RealmResource realmResource, ClientRepresentation clientRepresentation) {
        List<RoleRepresentation> roleRepresentation = new ArrayList<>();
        roles.forEach(role -> {
            RoleRepresentation clientRole = realmResource.clients().get(clientRepresentation.getId())
                    .roles().get(role.getRole()).toRepresentation();
            roleRepresentation.add(clientRole);
        });
        return roleRepresentation;
    }

    private UserResource getUserResource(String uuid, RealmResource realmResource) {
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(uuid);
        return userResource;
    }
}
