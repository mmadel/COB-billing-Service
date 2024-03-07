package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FetchPatientAuthorizationUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientAuthorization> find(Long patientId) {
        return patientAuthorizationRepository.findByPatient_Id(patientId).get().stream()
                .map(patientAuthorizationEntity -> {
                    PatientAuthorization patientAuthorization = mapper.map(patientAuthorizationEntity, PatientAuthorization.class);
                    String[] insCompany = {patientAuthorizationEntity.getPatientInsuranceCompany().toString(), patientAuthorizationEntity.getPatientInsuranceCompanyName()};
                    patientAuthorization.setInsCompany(insCompany);
                    return patientAuthorization;
                })
                .collect(Collectors.toList());
    }
}
