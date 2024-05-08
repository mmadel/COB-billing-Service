package com.cob.billing.usecases.user;

import com.cob.billing.model.security.User;
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

    public List<User> find() {
        return StreamSupport.stream(userRoleScopeRepository.findAll().spliterator(), false)
                .map(userRoleScopeEntity -> {
                    User user = new User();
                    user.setUuid(userRoleScopeEntity.getUuid());
                    user.setName("adel,mohamed");
                    return user;
                }).collect(Collectors.toList());
    }
}
