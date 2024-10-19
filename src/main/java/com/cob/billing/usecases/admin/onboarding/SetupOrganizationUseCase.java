package com.cob.billing.usecases.admin.onboarding;

import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.model.admin.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupOrganizationUseCase {
    @Autowired
    CreateOrganizationDatabaseUseCase createOrganizationDatabaseUseCase;
    @Autowired
    CreateOrganizationEntityUseCase createOrganizationEntityUseCase;
    @Autowired
    CreateClinicsUseCase createClinicsUseCase;
    @Autowired
    CreateAdministratorUserUseCase createAdministratorUserUseCase;

    @Transactional
    public void setup(Organization organization) throws OrganizationException {
        createOrganizationDatabaseUseCase.create();
        createOrganizationEntityUseCase.create(organization);
        createClinicsUseCase.create(organization.getClinics());
        createAdministratorUserUseCase.create(organization.getUser());
    }
}
