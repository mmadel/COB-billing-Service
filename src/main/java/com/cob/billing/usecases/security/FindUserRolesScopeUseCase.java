package com.cob.billing.usecases.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import com.cob.billing.model.security.RoleScope;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindUserRolesScopeUseCase {
    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;

    public List<RoleScope> find(String uuid, String[] roles) {
        UserRoleScopeEntity userRoleScope = userRoleScopeRepository.findByUuid(uuid).get();
        List<String> rolesList = Arrays.asList(roles);
        List<RoleScope> roleScopes = userRoleScope.getRoleScope();
        return roleScopes.stream()
                .filter(roleScope -> rolesList.contains(roleScope.getRole()))
                .collect(Collectors.toList());

    }
}
