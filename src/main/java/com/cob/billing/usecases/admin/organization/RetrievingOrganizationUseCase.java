package com.cob.billing.usecases.admin.organization;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.enums.OrganizationType;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.repositories.admin.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrievingOrganizationUseCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;

    public Organization findDefault() {
        OrganizationEntity entity = organizationRepository.findByType(OrganizationType.Default)
                .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
        return mapper.map(entity, Organization.class);
    }
}
