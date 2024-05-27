package com.cob.billing.usecases.security;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import com.cob.billing.usecases.security.kc.RemoveKeycloakUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteUserUseCase {
    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;
    @Autowired
    RemoveKeycloakUserUseCase removeKeycloakUserUseCase;
    @Transactional
    public void delete(String uuid) throws UserException {
        removeKeycloakUserUseCase.remove(uuid);
        userRoleScopeRepository.deleteUser(uuid);
    }
}
