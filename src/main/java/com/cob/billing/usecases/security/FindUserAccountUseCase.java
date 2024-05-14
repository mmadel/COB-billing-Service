package com.cob.billing.usecases.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindUserAccountUseCase {
    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;
    @Autowired
    ModelMapper mapper;

    public UserAccount findAccountUser(String uuid) {
        UserRoleScopeEntity entity = userRoleScopeRepository.findByUuid(uuid).get();
        return map(entity);
    }

    private UserAccount map(UserRoleScopeEntity entity) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUuid(entity.getUuid());
        userAccount.setName(entity.getName());
        userAccount.setRoleScope(entity.getRoleScope());
        return userAccount;
    }
}
