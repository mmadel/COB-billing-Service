package com.cob.billing.usecases.admin.onboarding;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.repositories.admin.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateClinicsUseCase {
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private ModelMapper mapper;

    public void create(List<Clinic> clinics) throws OrganizationException {
        try {
            List<ClinicEntity> entities = clinics.stream().map(clinic -> mapper.map(clinic, ClinicEntity.class)).collect(Collectors.toList());
            clinicRepository.saveAll(entities);
        } catch (Exception exception) {
            throw new OrganizationException(HttpStatus.CONFLICT, "", new Object[]{});
        }
    }
}
