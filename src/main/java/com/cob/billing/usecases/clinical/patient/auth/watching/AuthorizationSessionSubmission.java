package com.cob.billing.usecases.clinical.patient.auth.watching;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.clinical.patient.auth.watching.validation.ExecuteSessionAuthorizationValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSessionSubmission {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ExecuteSessionAuthorizationValidation sessionAuthorizationValidation;
    @Autowired
    ModelMapper mapper;
    public void submit(PatientSession patientSession) throws AuthorizationException {
        PatientSessionEntity session =  patientSessionRepository.findById(patientSession.getId()).get();
        if(session.getPatientAuthorization() != null){
            System.out.println("session attached, call validation COR");
            sessionAuthorizationValidation.check(patientSession,authorizationData(session.getPatientAuthorization()));
        }else{
            System.out.println("session not attached, call selection COR");
        }
    }
    private Long[] authorizationData(PatientAuthorizationEntity patientAuthorization){
        return new Long[] {patientAuthorization.getStartDateNumber(),patientAuthorization.getExpireDateNumber(),patientAuthorization.getPatientInsuranceCompany()};
    }
}
