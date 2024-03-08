package com.cob.billing.usecases.clinical.patient;

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
        if (!patientAuthorizations.isEmpty())
            patient.setAuthorizationDates(getPatientAuthorizationDates(patientAuthorizations));
    }

    private List<Long[]> getPatientAuthorizationDates(List<PatientAuthorization> patientAuthorizations) {
        List<Long[]> dates = new ArrayList<>();
        if (patientAuthorizations.size() == 1) {
            Long date[] = {patientAuthorizations.get(0).getStartDateNumber(), patientAuthorizations.get(0).getExpireDateNumber(), patientAuthorizations.get(0).getId()};
            dates.add(date);
        } else {
            for (int i = 0; i < patientAuthorizations.size(); i++) {
                Long date[] = {patientAuthorizations.get(0).getStartDateNumber(), patientAuthorizations.get(i).getExpireDateNumber(), patientAuthorizations.get(i).getId()};
                dates.add(date);
            }
        }
        return dates;
    }
}
