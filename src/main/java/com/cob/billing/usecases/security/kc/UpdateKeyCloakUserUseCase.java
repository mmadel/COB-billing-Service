package com.cob.billing.usecases.security.kc;

import com.cob.billing.model.security.UserAccount;
import com.cob.billing.usecases.security.kc.util.CompositeRolesNamesChecker;
import com.cob.billing.usecases.security.kc.util.ParentRolesNamesChecker;
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


        List<String> removedRoles = userAccount.getRoleScope().stream()
                .filter(roleScope -> roleScope.getScope().equals("hidden"))
                .map(roleScope -> roleScope.getRole())
                .collect(Collectors.toList());
        removedRoles.addAll(CompositeRolesNamesChecker.check(userAccount.getRoleScope()));
        removedRoles.addAll(ParentRolesNamesChecker.check(userAccount.getRoleScope()));
        if (removedRoles != null)
            unAssignKeycloakUserRolesUseCase.unAssign(userAccount.getUuid(), removedRoles, realmResource, clientRepresentation);

        List<String> addedRoles = userAccount.getRoleScope().stream()
                .filter(roleScope -> !roleScope.getScope().equals("hidden"))
                .map(roleScope -> roleScope.getRole())
                .collect(Collectors.toList());
        if (addedRoles != null)
            assignKeyCloakUserRolesUseCase.assign(userAccount.getUuid(), addedRoles, realmResource, clientRepresentation);
    }
}
