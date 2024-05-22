package com.cob.billing.usecases.security.kc;

import com.cob.billing.model.security.RoleScope;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RetrieveKeyCloakRoleRepresentationUseCase {
    public List<RoleRepresentation> find(List<RoleScope> roles, RealmResource realmResource, ClientRepresentation clientRepresentation){
        List<RoleRepresentation> roleRepresentation = new ArrayList<>();
        roles.stream()
                .forEach(role -> {
                    RoleRepresentation clientRole = realmResource.clients().get(clientRepresentation.getId())
                            .roles().get(role.getRole()).toRepresentation();
                    roleRepresentation.add(clientRole);
                });
        return roleRepresentation;
    }
}
