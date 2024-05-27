package com.cob.billing.usecases.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRoleScopeUseCase {
    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;
    @Autowired
    ModelMapper mapper;
    public void create(UserAccount userAccount){
        UserRoleScopeEntity toBeCreated = mapper.map(userAccount, UserRoleScopeEntity.class);
        userRoleScopeRepository.save(toBeCreated);

    }
}
