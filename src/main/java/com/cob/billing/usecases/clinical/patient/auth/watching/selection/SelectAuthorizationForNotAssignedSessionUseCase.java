package com.cob.billing.usecases.clinical.patient.auth.watching.selection;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.usecases.clinical.patient.auth.watching.submission.SubmitMatchedSessionAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SelectAuthorizationForNotAssignedSessionUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    SubmitMatchedSessionAuthorizationUseCase submitMatchedSessionAuthorizationUseCase;

    public void select(SubmissionSession submissionSession) throws AuthorizationException {
        List<PatientAuthorizationEntity> patientAuthorizations = patientAuthorizationRepository.findByPatient_Id(null).get();
        List<PatientAuthorizationEntity> patientAuthorization = new ArrayList<>();
        if (patientAuthorizations.size() == 1) {
            System.out.println("assign auth to session");
            Long startDate = patientAuthorizations.stream().findFirst().get().getStartDateNumber();
            Long expiryDate = patientAuthorizations.stream().findFirst().get().getExpireDateNumber();
//            if (!(submissionSession.getPatientSession().getServiceDate() >= startDate && submissionSession.getPatientSession().getServiceDate() <= expiryDate))
//                throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OUT_OF_RANGE, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});
//            else
//                patientAuthorization.add(patientAuthorizations.stream().findFirst().get());
        } else {
            System.out.println("select from auths to assign to session");
            for (PatientAuthorizationEntity authorization : patientAuthorizations) {
//                if (submissionSession.getPatientSession().getServiceDate() >= authorization.getStartDateNumber() && submissionSession.getPatientSession().getServiceDate() <= authorization.getExpireDateNumber())
//                    patientAuthorization.add(authorization);
            }
        }
//        if (patientAuthorization.isEmpty())
//            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_NO_MATCHED_AUTH, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});
//        else if (patientAuthorization.size() > 1)
//            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OVERLAP, new Object[]{submissionSession.getPatientSession().getServiceDate().toString()});
//        else
//            submitMatchedSessionAuthorizationUseCase.submit(submissionSession, patientAuthorization.get(0).getId(), patientAuthorization.get(0).getAuthNumber());
    }
}
