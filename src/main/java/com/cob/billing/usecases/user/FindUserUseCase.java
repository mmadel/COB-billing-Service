package com.cob.billing.usecases.user;

import com.cob.billing.model.security.UserAccount;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindUserUseCase {
    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;

    public List<UserAccount> find() {
        return StreamSupport.stream(userRoleScopeRepository.findAll().spliterator(), false)
                .map(userRoleScopeEntity -> {
                    UserAccount userAccount = new UserAccount();
                    userAccount.setId(userRoleScopeEntity.getId());
                    userAccount.setUuid(userRoleScopeEntity.getUuid());
                    userAccount.setName(userRoleScopeEntity.getName());
                    userAccount.setUserAccount(userRoleScopeEntity.getUserAccount());
                    userAccount.setEmail(userRoleScopeEntity.getEmail());
                    userAccount.setRoleScope(userRoleScopeEntity.getRoleScope());
                    return userAccount;
                }).collect(Collectors.toList());
    }
}
