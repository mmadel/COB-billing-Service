package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("AssignHandler")
public class AuthorizationAssignHandler implements AuthorizationHandling {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) throws AuthorizationException {
        if (!request.getPatientInformation().getAuthorizationInformation().getSelected()) {
            Long[] selectedAuthorization = request.getPatientInformation().getAuthorizationSelection().getAuthorizations().stream().findFirst().get();
            Long expiryDate = selectedAuthorization[1];
            Long authorizationId = selectedAuthorization[2];
            PatientAuthorizationEntity patientAuthorization = patientAuthorizationRepository.findById(authorizationId).get();
            request.getPatientInformation().getAuthorizationSelection().setExpiryDate(expiryDate);
            request.getPatientInformation().getAuthorizationSelection().setRemainingCounter(patientAuthorization.getRemaining());
        }

        nextAuthorizationHandling.processRequest(request);
    }
}
