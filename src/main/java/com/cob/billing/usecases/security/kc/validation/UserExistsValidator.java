package com.cob.billing.usecases.security.kc.validation;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.security.kc.KeyCloakUser;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UserExistsValidator extends KeyCloakUserValidator{
    @Override
    public boolean validate(KeyCloakUser keyCloakUser) throws UserException {
        List<UserRepresentation> userRepresentations = keyCloakUser.getRealmResource().users().list();
        for (UserRepresentation userRepresentation : userRepresentations) {
            if (userRepresentation.getUsername().equals(keyCloakUser.getUserName()))
                throw new UserException(HttpStatus.CONFLICT, UserException.USER_IS_EXISTS, new Object[]{keyCloakUser.getUserName()});
        }
        return validateNext(keyCloakUser);
    }
}
