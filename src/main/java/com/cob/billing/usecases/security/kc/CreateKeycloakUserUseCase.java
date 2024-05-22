package com.cob.billing.usecases.security.kc;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.security.RoleScope;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.model.security.kc.KeyCloakUser;
import com.cob.billing.usecases.security.kc.validation.KeyCloakUserValidator;
import com.cob.billing.usecases.security.kc.validation.UserEmailValidator;
import com.cob.billing.usecases.security.kc.validation.UserExistsValidator;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CreateKeycloakUserUseCase {
    @Autowired
    Keycloak keycloakService;

    @Value("${kc.realm}")
    private String realm;
    @Value("${kc.billing.client}")
    private String billingClient;
    @Autowired
    CreateKCUserResourceUseCase createKCUserResourceUseCase;
    @Autowired
    CreateUserCredentialsUseCase createUserCredentialsUseCase;
    @Autowired
    AssignKeyCloakUserRolesUseCase assignKeyCloakUserRolesUseCase;

    private String uuid;

    public void create(UserAccount userAccount) throws UserException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        RealmResource realmResource = keycloakService.realm(realm);
        KeyCloakUser keyCloakUser = convertToKeycloakUser(userAccount, realmResource);
        KeyCloakUserValidator validator = KeyCloakUserValidator.register(
                new UserExistsValidator(),
                new UserEmailValidator()
        );
        validator.validate(keyCloakUser);

        createKCUserResourceUseCase.create(keyCloakUser, realmResource);
        userAccount.setUuid(createKCUserResourceUseCase.getUserUUID());
        createUserCredentialsUseCase.create(createKCUserResourceUseCase.getUserUUID(), keyCloakUser.getPassword());
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(billingClient).get(0);
        List<RoleScope> filteredNotHiddenList = keyCloakUser.getRoleScope().stream()
                .filter(roleScope -> !roleScope.getScope().equals("hidden"))
                .collect(Collectors.toList());
        assignKeyCloakUserRolesUseCase.assign(createKCUserResourceUseCase.getUserUUID(), filteredNotHiddenList, realmResource, clientRepresentation);
    }

    private KeyCloakUser convertToKeycloakUser(UserAccount userAccount, RealmResource realmResource) {
        return KeyCloakUser.builder()
                .firstName(userAccount.getName().split(",")[0])
                .firstName(userAccount.getName().split(",")[1])
                .email(userAccount.getEmail())
                .userName(userAccount.getUserAccount())
                .password(userAccount.getPassword())
                .realmResource(realmResource)
                .roleScope(userAccount.getRoleScope())
                .build();
    }
}
