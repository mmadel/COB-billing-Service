package com.cob.billing.repositories.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleScopeRepository extends CrudRepository<UserRoleScopeEntity, Long> {

    Optional<UserRoleScopeEntity> findByUuid(String uuid);
}
