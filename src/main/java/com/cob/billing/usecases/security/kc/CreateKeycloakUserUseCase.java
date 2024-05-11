package com.cob.billing.usecases.security.kc;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.model.security.kc.KeyCloakUser;
import com.cob.billing.usecases.security.kc.validation.KeyCloakUserValidator;
import com.cob.billing.usecases.security.kc.validation.UserEmailValidator;
import com.cob.billing.usecases.security.kc.validation.UserExistsValidator;
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

    public void create(UserAccount userAccount) throws UserException {
        RealmResource realmResource = keycloakService.realm(realm);
        ClientRepresentation clientRepresentation = null;
        try {
            clientRepresentation = realmResource.clients().findByClientId(billingClient).get(0);
        } catch (javax.ws.rs.NotAuthorizedException exception) {
            log.warn("admin token is expired");
        }
        UsersResource usersResource = realmResource.users();
        KeyCloakUser keyCloakUser = convertToKeycloakUser(userAccount, realmResource);
        KeyCloakUserValidator validator = KeyCloakUserValidator.register(
                new UserExistsValidator(),
                new UserEmailValidator()
        );
        validator.validate(keyCloakUser);
    }

    private KeyCloakUser convertToKeycloakUser(UserAccount userAccount, RealmResource realmResource) {
        return KeyCloakUser.builder()
                .firstName(userAccount.getName().split(",")[0])
                .firstName(userAccount.getName().split(",")[1])
                .email(userAccount.getEmail())
                .userName(userAccount.getUserAccount())
                .realmResource(realmResource)
                .roleScope(userAccount.getRoleScope())
                .build();
    }
}
