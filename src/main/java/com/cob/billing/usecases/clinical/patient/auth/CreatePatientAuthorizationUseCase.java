package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePatientAuthorizationUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    ModelMapper mapper;

    public Long createOrUpdate(PatientAuthorization model) {
        PatientEntity patient = patientRepository.findById(model.getPatientId()).get();
        PatientAuthorizationEntity toBeCreated = mapper.map(model, PatientAuthorizationEntity.class);
        toBeCreated.setPatientInsuranceCompany(Long.parseLong(model.getInsCompany()[1]));
        toBeCreated.setPatientInsuranceCompanyName(model.getInsCompany()[0]);
        toBeCreated.setPatient(patient);
//        if (!(model.getId() != null))
//            //toBeCreated.setSelected(false);
        return patientAuthorizationRepository.save(toBeCreated).getId();
    }
}
