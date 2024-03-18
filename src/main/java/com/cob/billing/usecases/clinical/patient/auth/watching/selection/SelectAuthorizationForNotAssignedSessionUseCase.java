package com.cob.billing.usecases.clinical.patient.auth.watching.selection;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.usecases.clinical.patient.auth.watching.submission.SubmitMatchedSessionAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectAuthorizationForNotAssignedSessionUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    SubmitMatchedSessionAuthorizationUseCase submitMatchedSessionAuthorizationUseCase;

    public void select(SubmissionSession submissionSession) throws AuthorizationException {
        List<PatientAuthorizationEntity> patientAuthorizations = patientAuthorizationRepository.findByPatient_Id(submissionSession.getPatientId()).get();
        PatientAuthorizationEntity patientAuthorization = null;
        if (patientAuthorizations.size() == 1) {
            System.out.println("assign this auth to session");
            patientAuthorization = patientAuthorizations.stream().findFirst().get();
            if (!(submissionSession.getPatientSession().getServiceDate() >= patientAuthorization.getStartDateNumber() && submissionSession.getPatientSession().getServiceDate() <= patientAuthorization.getExpireDateNumber()))
                throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});
        } else {

        }
        if (patientAuthorization != null)
            submitMatchedSessionAuthorizationUseCase.submit(submissionSession, patientAuthorization.getId(), patientAuthorization.getAuthNumber());
    }
}
