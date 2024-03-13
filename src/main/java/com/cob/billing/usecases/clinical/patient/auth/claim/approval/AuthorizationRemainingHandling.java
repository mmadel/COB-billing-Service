package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("RemainingHandling")
public class AuthorizationRemainingHandling implements AuthorizationHandling {

    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    private AuthorizationHandling nextAuthorizationHandling;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    @Transactional
    public void processRequest(InvoiceRequest request) throws AuthorizationException {
        if (!(request.getPatientInformation().getAuthorizationSelection().getRemainingCounter() >= 1)) {
            throw new AuthorizationException(HttpStatus.CONFLICT, AuthorizationException.AUTH_CREDIT_REMAINING,new Object[]{""});
        }else{
            PatientAuthorizationEntity patientAuthorizationEntity = patientAuthorizationRepository.findById(request.getPatientInformation().getAuthorizationSelection().getAuthorizationId()).get();
            int remaining  = patientAuthorizationEntity.getRemaining() -1;
            patientAuthorizationEntity.setRemaining(remaining);
            patientAuthorizationRepository.save(patientAuthorizationEntity);
        }

    }
}
