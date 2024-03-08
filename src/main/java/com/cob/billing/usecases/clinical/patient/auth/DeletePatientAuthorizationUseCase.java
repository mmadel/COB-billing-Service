package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DeletePatientAuthorizationUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    public void delete(Long authId){
        patientAuthorizationRepository.deleteById(authId);
    }
}
