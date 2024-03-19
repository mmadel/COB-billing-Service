package com.cob.billing.usecases.clinical.patient.auth.watching.selection;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.auth.AuthorizationSession;
import com.cob.billing.model.bill.auth.SubmissionSession;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.AuthorizationValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.InsuranceCompanyValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.OutOfCreditValidator;
import com.cob.billing.usecases.clinical.patient.auth.watching.validator.tmp.OutOfRangeValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class LazySessionAuthorizationExecutor extends SessionAuthorizationExecutor {
    @Override
    public void execute(SubmissionSession submissionSession) throws AuthorizationException {
        List<AuthorizationSession> patientAuthorizations = submissionSession.getPatientAuthorizations();
        if (patientAuthorizations.size() == 1)
            executeOnSingleAuthorization(submissionSession, patientAuthorizations.stream().findFirst().get());
        else
            executeOnMultipleAuthorizations(submissionSession, patientAuthorizations);
    }

    private void executeOnSingleAuthorization(SubmissionSession submissionSession, AuthorizationSession authorizationSession) throws AuthorizationException {
        submissionSession.setAuthorizationSession(authorizationSession);
        AuthorizationValidator validator = AuthorizationValidator.register(
                new OutOfRangeValidator(),
                new OutOfCreditValidator(),
                new InsuranceCompanyValidator()
        );
        validator.validate(submissionSession);
        deduct(submissionSession);
    }

    private void executeOnMultipleAuthorizations(SubmissionSession submissionSession, List<AuthorizationSession> patientAuthorizations) throws AuthorizationException {
        List<AuthorizationSession> patientAuthorization = new ArrayList<>();
        for (AuthorizationSession authorization : patientAuthorizations) {
            if (submissionSession.getDateOfService() >= authorization.getStartDate()
                    && submissionSession.getDateOfService() <= authorization.getExpiryDate()
                    && submissionSession.getInsuranceCompanyId().equals(authorization.getInsuranceCompanyId()))
                patientAuthorization.add(authorization);
        }
        if (patientAuthorization.isEmpty())
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_NO_MATCHED_AUTH, new Object[]{submissionSession.getDateOfService().toString()});

        if (patientAuthorization.size() > 1)
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.SESSION_AUTH_OVERLAP, new Object[]{submissionSession.getDateOfService().toString()});

        submissionSession.setAuthorizationSession(patientAuthorization.stream().findFirst().get());

        AuthorizationValidator validator = AuthorizationValidator.register(
                new OutOfCreditValidator()
        );
        validator.validate(submissionSession);
        deduct(submissionSession);
    }
}
