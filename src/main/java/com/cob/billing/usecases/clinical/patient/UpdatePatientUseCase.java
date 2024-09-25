package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.model.clinical.patient.update.profile.PatientCaseDTO;
import com.cob.billing.model.clinical.patient.update.profile.PatientInsuranceDTO;
import com.cob.billing.model.clinical.patient.update.profile.PatientSessionDTO;
import com.cob.billing.model.clinical.patient.update.profile.UpdateProfileDTO;
import com.cob.billing.usecases.clinical.patient.cases.CreatePatientCaseUseCase;
import com.cob.billing.usecases.clinical.patient.cases.DeletePatientCaseUseCase;
import com.cob.billing.usecases.clinical.patient.insurance.company.CreatePatientInsuranceCompanyUseCase;
import com.cob.billing.usecases.clinical.patient.insurance.company.DeletePatientInsuranceCompanyUseCase;
import com.cob.billing.usecases.clinical.patient.session.CreatePatientSessionUseCase;
import com.cob.billing.usecases.clinical.patient.session.UpdatePatientSessionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientUseCase {
    @Autowired
    CreatePatientInsuranceCompanyUseCase createPatientInsuranceCompanyUseCase;
    @Autowired
    DeletePatientInsuranceCompanyUseCase deletePatientInsuranceCompanyUseCase;
    @Autowired
    CreatePatientCaseUseCase createPatientCaseUseCase;
    @Autowired
    DeletePatientCaseUseCase deletePatientCaseUseCase;
    @Autowired
    CreatePatientSessionUseCase createPatientSessionUseCase;
    @Autowired
    UpdatePatientSessionUseCase updatePatientSessionUseCase;
    @Autowired
    CreatePatientUseCase createPatientUseCase;

    public void update(UpdateProfileDTO profile) {
        updateInsurances(profile.getInsurances(), profile.getPatient().getId());
        updateCases(profile.getCases(),profile.getPatient().getId());
        updateSessions(profile.getSessions(), profile.getPatient().getId());
        createPatientUseCase.create(profile.getPatient());
    }

    private void updateInsurances(List<PatientInsuranceDTO> insurances, Long patientId) {
        insurances.forEach(patientInsuranceDTO -> {
            if (patientInsuranceDTO.getOperation() == 0 || patientInsuranceDTO.getOperation() == 1)
                createPatientInsuranceCompanyUseCase.create(patientInsuranceDTO.getPatientInsurance(), patientId);
            if (patientInsuranceDTO.getOperation() == 2)
                deletePatientInsuranceCompanyUseCase.delete(patientInsuranceDTO.getPatientInsurance().getId()
                        , patientInsuranceDTO.getPatientInsurance().getVisibility());

        });
    }

    private void updateCases(List<PatientCaseDTO> cases,Long patientId) {
        cases.forEach(patientCaseDTO->{
            if (patientCaseDTO.getOperation() == 0 || patientCaseDTO.getOperation() == 1)
                createPatientCaseUseCase.create(patientCaseDTO.getPatientCase(),patientId);
            if (patientCaseDTO.getOperation() == 2)
                deletePatientCaseUseCase.delete(patientCaseDTO.getPatientCase().getId());
        });

    }

    private void updateSessions(List<PatientSessionDTO> sessions,Long patientId) {
        sessions.forEach(patientSessionDTO -> {
            if (patientSessionDTO.getOperation() == 0)
                createPatientSessionUseCase.create(patientSessionDTO.getPatientSession());
            if(patientSessionDTO.getOperation() == 1)
                updatePatientSessionUseCase.update(patientSessionDTO.getPatientSession());
//            if (patientSessionDTO.getOperation() == 2)
//                System.out.println();
        });
    }
}
