package com.cob.billing.usecases.admin.organization;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.repositories.admin.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrganizationUseCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;
    public Long create(Organization organization){
        OrganizationEntity toBeCreated = mapper.map(organization, OrganizationEntity.class);
        return organizationRepository.save(toBeCreated).getId();
    }
}
