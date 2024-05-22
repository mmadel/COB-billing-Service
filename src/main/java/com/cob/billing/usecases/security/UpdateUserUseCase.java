package com.cob.billing.usecases.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.repositories.security.UserRoleScopeRepository;
import com.cob.billing.usecases.security.kc.UpdateKeyCloakUserUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@Component
public class UpdateUserUseCase {
    @Autowired
    UpdateKeyCloakUserUseCase updateKeyCloakUserUseCase;

    @Autowired
    UserRoleScopeRepository userRoleScopeRepository;
    @Autowired
    ModelMapper mapper;
    @Transactional
    public void update(UserAccount userAccount) {
        UserRoleScopeEntity toBeUpdated = mapper.map(userAccount , UserRoleScopeEntity.class);
        userRoleScopeRepository.save(toBeUpdated);
        updateKeyCloakUserUseCase.updateKyCloakUser(userAccount);
    }
}
