package com.cob.billing.usecases.admin.organization;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.repositories.admin.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrganizationUseCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;

    public Long update(Organization organization) {
        OrganizationEntity toBeUpdated = mapper.map(organization, OrganizationEntity.class);
        return organizationRepository.save(toBeUpdated).getId();
    }
}
