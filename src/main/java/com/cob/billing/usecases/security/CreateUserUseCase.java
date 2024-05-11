package com.cob.billing.usecases.security;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.usecases.security.kc.CreateKeycloakUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase {
    @Autowired
    CreateKeycloakUserUseCase createKeycloakUserUseCase;
    @Autowired
    CreateUserRoleScopeUseCase createUserRoleScopeUseCase;

    public void create(UserAccount userAccount) throws UserException {
        createKeycloakUserUseCase.create(userAccount);
        createUserRoleScopeUseCase.create(userAccount);
    }
}
