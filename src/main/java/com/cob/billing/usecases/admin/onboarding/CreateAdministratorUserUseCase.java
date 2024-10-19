package com.cob.billing.usecases.admin.onboarding;

import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.usecases.security.CreateUserRoleScopeUseCase;
import com.cob.billing.usecases.security.DisableUserUseCase;
import com.cob.billing.usecases.security.kc.CreateKeycloakUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CreateAdministratorUserUseCase {
    @Autowired
    private CreateKeycloakUserUseCase createKeycloakUserUseCase;
    @Autowired
    private CreateUserRoleScopeUseCase createUserRoleScopeUseCase;
    @Autowired
    private DisableUserUseCase disableUserUseCase;

    @Value("${administrator.uuid}")
    private String uuid;

    public void create(UserAccount userAccount) throws OrganizationException {
        try {
            createKeycloakUserUseCase.create(userAccount);
            createUserRoleScopeUseCase.create(userAccount);
            disableUserUseCase.disable(uuid);

        } catch (Exception exception) {
            throw new OrganizationException(HttpStatus.CONFLICT, "", new Object[]{});
        }
    }
}
