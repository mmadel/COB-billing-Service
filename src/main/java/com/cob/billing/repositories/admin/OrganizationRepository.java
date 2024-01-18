package com.cob.billing.repositories.admin;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.enums.OrganizationType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
    Optional<OrganizationEntity> findByType(OrganizationType type);
}
