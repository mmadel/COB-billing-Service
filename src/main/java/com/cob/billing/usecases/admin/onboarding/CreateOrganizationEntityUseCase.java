package com.cob.billing.usecases.admin.onboarding;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.repositories.admin.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CreateOrganizationEntityUseCase {
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ModelMapper mapper;
    public void create(Organization organization) throws OrganizationException {
        try {
            OrganizationEntity toBeCreated = mapper.map(organization, OrganizationEntity.class);
            organizationRepository.save(toBeCreated).getId();
        } catch (Exception exception) {
            throw new OrganizationException(HttpStatus.CONFLICT, "", new Object[]{});
        }
    }
}
