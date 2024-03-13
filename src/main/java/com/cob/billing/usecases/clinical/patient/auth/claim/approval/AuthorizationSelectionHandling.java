package com.cob.billing.usecases.clinical.patient.auth.claim.approval;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("SelectionHandling")
public class AuthorizationSelectionHandling implements AuthorizationHandling {

    private AuthorizationHandling nextAuthorizationHandling;

    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;

    @Override
    public void setNextHandler(AuthorizationHandling nextHandler) {
        this.nextAuthorizationHandling = nextHandler;
    }

    @Override
    public void processRequest(InvoiceRequest invoiceRequest) throws AuthorizationException {
        if (invoiceRequest.getPatientInformation().getAuthorizationInformation().getSelected())
            pickSelectedAuthorization(invoiceRequest);
        else
            selectAuthorization(invoiceRequest);
    }

    private List<Long[]> selectAuthorization(Long[] authorizationData, Long insuranceCompanyId, List<SelectedSessionServiceLine> serviceLines) {
        List<Long[]> selectedAuthorizations = new ArrayList<>();
        List<PatientSession> sessions = serviceLines.stream().map(serviceLine -> serviceLine.getSessionId()).collect(Collectors.toList());
        for (int i = 0; i < sessions.size(); i++) {
            PatientSession patientSession = sessions.get(i);
            if (patientSession.getServiceDate() >= authorizationData[0] && patientSession.getServiceDate() <= authorizationData[1] && authorizationData[3].equals(insuranceCompanyId)) {
                /*
                        [0] start date
                        [1] expiry date
                        [2] authorization id
                        [3] session id
                 */
                Long[] authorization = {authorizationData[0], authorizationData[1], authorizationData[2], patientSession.getId()};
                selectedAuthorizations.add(authorization);
            }
        }
        return selectedAuthorizations;
    }

    private void pickSelectedAuthorization(InvoiceRequest request) {
        Optional<PatientAuthorizationEntity> patientAuthorizationEntity;
        patientAuthorizationEntity = patientAuthorizationRepository.findByPatient_Id(request.getPatientInformation().getId()).get()
                .stream()
                .filter(patientAuthorization -> patientAuthorization.getSelected())
                .findFirst();
        if (!patientAuthorizationEntity.isEmpty()) {
            PatientAuthorizationEntity patientAuthorization = patientAuthorizationEntity.get();
            request.getPatientInformation().getAuthorizationSelection().setAuthorizationNumber(patientAuthorization.getAuthNumber());
            request.getPatientInformation().getAuthorizationSelection().setRemainingCounter(patientAuthorization.getRemaining());
            request.getPatientInformation().getAuthorizationSelection().setExpiryDate(patientAuthorization.getExpireDateNumber());
        }
    }

    private void selectAuthorization(InvoiceRequest invoiceRequest) throws AuthorizationException {
        List<Long[]> selectedAuthorizations = new ArrayList<>();
        List<Long[]> authorizations = invoiceRequest.getPatientInformation().getAuthorizationInformation().getAuthorizationsMetaData();
        if (authorizations.size() == 1)
            selectedAuthorizations.addAll(selectAuthorization(authorizations.stream().findFirst().get()
                    , invoiceRequest.getInvoiceInsuranceCompanyInformation().getId(), invoiceRequest.getSelectedSessionServiceLine()));
        else
            for (int i = 0; i < authorizations.size(); i++) {
                selectedAuthorizations.addAll(selectAuthorization(authorizations.get(i)
                        , invoiceRequest.getInvoiceInsuranceCompanyInformation().getId(), invoiceRequest.getSelectedSessionServiceLine()));
            }

        if (selectedAuthorizations != null && selectedAuthorizations.size() > 0) {
            invoiceRequest.getPatientInformation().getAuthorizationSelection().setAuthorizations(selectedAuthorizations);
            nextAuthorizationHandling.processRequest(invoiceRequest);
        }
    }
}
