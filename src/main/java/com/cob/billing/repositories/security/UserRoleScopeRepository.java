package com.cob.billing.repositories.security;

import com.cob.billing.entity.security.UserRoleScopeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRoleScopeRepository extends CrudRepository<UserRoleScopeEntity, Long> {
    Optional<UserRoleScopeEntity> findByUuid(String uuid);
    @Modifying
    @Query("delete from UserRoleScopeEntity u where u.uuid =:uuid")
    void deleteUser(@Param("uuid") String uuid);
}
