package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindPatientInsuranceCompanyUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;
    @Autowired
    MapPatientInsurancesUseCase mapPatientInsurancesUseCase;

    public List<PatientInsurance> find(Long patientId) {
        List<PatientInsuranceEntity> patientInsuranceEntities = patientInsuranceRepository.find(patientId);
        return mapPatientInsurancesUseCase.map(patientInsuranceEntities);
    }
}
