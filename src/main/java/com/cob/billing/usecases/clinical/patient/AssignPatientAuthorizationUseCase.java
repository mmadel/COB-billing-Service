package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.model.bill.invoice.tmp.auth.AuthorizationInformation;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.usecases.clinical.patient.auth.FetchPatientAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignPatientAuthorizationUseCase {
    @Autowired
    private FetchPatientAuthorizationUseCase fetchPatientAuthorizationUseCase;

    public void find(Patient patient) {
        List<PatientAuthorization> patientAuthorizations = fetchPatientAuthorizationUseCase.find(patient.getId());
        if (!patientAuthorizations.isEmpty()) {
            AuthorizationInformation authorizationInformation = new AuthorizationInformation();
            for (int i = 0; i < patientAuthorizations.size(); i++) {
                Long authorizationStartDate = patientAuthorizations.get(i).getStartDateNumber();
                Long authorizationExpiryDate = patientAuthorizations.get(i).getExpireDateNumber();
                Long authorizationId = patientAuthorizations.get(i).getId();
                Long insuranceCompanyId = Long.parseLong(patientAuthorizations.get(i).getInsCompany()[0]);
                Long[] authorizationMetaData = {authorizationStartDate, authorizationExpiryDate, authorizationId, insuranceCompanyId};
                authorizationInformation.getAuthorizationsMetaData().add(authorizationMetaData);
                authorizationInformation.setTurning(patient.getAuthorizationInformation().getTurning());
            }
            patient.setAuthorizationInformation(authorizationInformation);
        }
    }
}
