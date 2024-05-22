package com.cob.billing.usecases.security.kc;

import com.cob.billing.model.security.RoleScope;
import com.cob.billing.model.security.UserAccount;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateKeyCloakUserUseCase {
    @Autowired
    Keycloak keycloakService;
    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.billing.client}")
    private String billingClient;

    @Autowired
    UnAssignKeycloakUserRolesUseCase unAssignKeycloakUserRolesUseCase;
    @Autowired
    AssignKeyCloakUserRolesUseCase assignKeyCloakUserRolesUseCase;

    public void updateKyCloakUser(UserAccount userAccount) {
        RealmResource realmResource = keycloakService.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(billingClient).get(0);
        List<RoleScope> removedRolesList = userAccount.getRoleScope().stream()
                .filter(roleScope -> roleScope.getScope().equals("hidden"))
                .collect(Collectors.toList());
        if (removedRolesList != null)
            unAssignKeycloakUserRolesUseCase.unAssign(userAccount.getUuid(), removedRolesList, realmResource, clientRepresentation);

        List<RoleScope> addedRolesList = userAccount.getRoleScope().stream()
                .filter(roleScope -> !roleScope.getScope().equals("hidden"))
                .collect(Collectors.toList());
        if (addedRolesList != null)
            assignKeyCloakUserRolesUseCase.assign(userAccount.getUuid(), addedRolesList, realmResource, clientRepresentation);

    }
}
