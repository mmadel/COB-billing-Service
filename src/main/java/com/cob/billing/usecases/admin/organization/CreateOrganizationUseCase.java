package com.cob.billing.usecases.admin.organization;

import com.cob.billing.entity.admin.OrganizationEntity;
import com.cob.billing.enums.OrganizationType;
import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.admin.Organization;
import com.cob.billing.repositories.admin.OrganizationRepository;
import com.cob.billing.usecases.admin.clinic.CreateClinicUseCase;
import com.cob.billing.usecases.init.script.DatabaseInitUseCase;
import com.cob.billing.usecases.security.CreateUserUseCase;
import com.cob.billing.usecases.security.DisableUserUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class CreateOrganizationUseCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CreateUserUseCase createUserUseCase;
    @Autowired
    CreateClinicUseCase createClinicUseCase;
    @Autowired
    DisableUserUseCase disableUserUseCase;
    @Autowired
    DatabaseInitUseCase databaseInitUseCase;
    @Value("${administrator.uuid}")
    private String administratorUUID;
    @Transactional
    public Long create(Organization organization) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        Optional<OrganizationEntity> defaultOrganization = organizationRepository.findByType(OrganizationType.Default);
        if (defaultOrganization.isPresent())
            organization.setType(OrganizationType.Other);
        else
            organization.setType(OrganizationType.Default);
        OrganizationEntity toBeCreated = mapper.map(organization, OrganizationEntity.class);
        //Save Organization
        Long organizationId = organizationRepository.save(toBeCreated).getId();
        //Save Administrator User
        createUserUseCase.create(organization.getUser());
        //Save Clinics
        createClinicUseCase.create(organization.getClinics());
        //Disable Administrator User
        disableUserUseCase.disable(administratorUUID);
        //execute lookup insertion
        databaseInitUseCase.executeInsertLookupScript();

        return organizationId;
    }
}
