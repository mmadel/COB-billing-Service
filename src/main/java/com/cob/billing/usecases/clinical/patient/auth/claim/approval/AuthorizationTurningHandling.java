package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("TurningHandling")
public class AuthorizationTurningHandling implements AuthorizationHandling {
    private AuthorizationHandling nextAuthorizationHandling;
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest request) {
        if (!request.getPatientInformation().getAuthorizationInformation().getTurning()) {
            nextAuthorizationHandling.processRequest(request);
        } else {
            PatientAuthorizationEntity patientAuthorizationEntity =
                    patientAuthorizationRepository.findByPatient_Id(request.getPatientInformation().getId()).get()
                            .stream()
                            .filter(patientAuthorization -> patientAuthorization.getSelected())
                            .findFirst().get();
            if (patientAuthorizationEntity.getAuthNumber() != null)
                request.getPatientInformation().getAuthorizationSelection().setAuthorizationNumber(patientAuthorizationEntity.getAuthNumber());
        }

    }
}
