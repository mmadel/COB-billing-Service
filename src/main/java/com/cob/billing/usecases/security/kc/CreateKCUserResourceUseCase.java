package com.cob.billing.usecases.security.kc;

import com.cob.billing.configuration.onboarding.SchemaTenantContext;
import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.security.kc.KeyCloakUser;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CreateKCUserResourceUseCase {
    private String userUUID;

    public void create(KeyCloakUser keyCloakUser, RealmResource realmResource) throws UserException {
        UsersResource usersResource = realmResource.users();
        UserRepresentation userRepresentation = createUserRepresentation(keyCloakUser);
        Response response = usersResource.create(userRepresentation);
        try {
            userUUID = CreatedResponseUtil.getCreatedId(response);

        } catch (WebApplicationException ex) {
            log.error("check the Keycloak ", ex.getMessage());
            throw new UserException(HttpStatus.CONFLICT, UserException.USER_NOT_CREATED_IN_KC, new Object[]{keyCloakUser.getUserName()});
        }
    }

    private UserRepresentation createUserRepresentation(KeyCloakUser keyCloakUser) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(keyCloakUser.getUserName());
        userRepresentation.setFirstName(keyCloakUser.getFirstName());
        userRepresentation.setLastName(keyCloakUser.getLastName());
        userRepresentation.setEmail(keyCloakUser.getEmail());
        return userRepresentation;
    }

    public String getUserUUID() {
        return userUUID;
    }
}
