package com.cob.billing.usecases.security.kc;

import com.cob.billing.model.security.RoleScope;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssignKeyCloakUserRolesUseCase {

    @Autowired
    private RetrieveKeyCloakRoleRepresentationUseCase retrieveKeyCloakRoleRepresentationUseCase;

    public void assign(String uuid, List<String> roles,RealmResource realmResource,ClientRepresentation clientRepresentation) {
        List<RoleRepresentation> roleRepresentations = retrieveKeyCloakRoleRepresentationUseCase.find(roles, realmResource, clientRepresentation);
        getUserResource(uuid, realmResource).roles().clientLevel(clientRepresentation.getId()).add(roleRepresentations);
    }


    private UserResource getUserResource(String uuid, RealmResource realmResource) {
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(uuid);
        return userResource;
    }
}
