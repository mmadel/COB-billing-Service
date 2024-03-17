package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSessionSubmission {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    public void submit(PatientSession patientSession){
        PatientSessionEntity session =  patientSessionRepository.findById(patientSession.getId()).get();
        if(session.getPatientAuthorization() != null){
            System.out.println("session attached, call validation COR");
        }else{
            System.out.println("session not attached, call selection COR");
        }
    }
}