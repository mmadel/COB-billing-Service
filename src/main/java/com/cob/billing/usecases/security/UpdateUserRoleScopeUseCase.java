package com.cob.billing.usecases.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import com.cob.billing.model.security.UserRoleScope;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateUserRoleScopeUseCase {
    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;
    @Autowired
    ModelMapper mapper;
    @Transactional
    public void update(UserRoleScope userRoleScope){
        userRoleScopeRepository.deleteById(userRoleScope.getId());
        UserRoleScopeEntity toBeUpdated = mapper.map(userRoleScope, UserRoleScopeEntity.class);
        userRoleScopeRepository.save(toBeUpdated);
    }
}
