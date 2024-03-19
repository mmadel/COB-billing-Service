package com.cob.billing.usecases.clinical.patient.auth.watching.submission;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmitMatchedSessionAuthorizationUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    public void submit(SubmissionSession submissionSession , Long authorizationId , String authorizationNumber) {
        deduct(authorizationId);
        assign(submissionSession,authorizationNumber);
    }

    private void deduct(Long authorizationId) {
        PatientAuthorizationEntity patientAuthorizationEntity = patientAuthorizationRepository.findById(authorizationId).get();
        int remaining = patientAuthorizationEntity.getRemaining() - 1;
        patientAuthorizationEntity.setRemaining(remaining);
        patientAuthorizationRepository.save(patientAuthorizationEntity);
    }

    private void assign(SubmissionSession submissionSession , String authorizationNumber) {
        //submissionSession.getPatientSession().setAuthorizationNumber(authorizationNumber);
        System.out.println();
    }
}
