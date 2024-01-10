package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInsuranceExternalCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInsuranceInternalCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletePatientInsuranceCompanyUseCase {
    @Autowired
    PatientInsuranceExternalCompanyRepository patientInsuranceExternalCompanyRepository;
    @Autowired
    PatientInsuranceInternalCompanyRepository patientInsuranceInternalCompanyRepository;

    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;

    @Transactional
    public boolean delete(Long Id , InsuranceCompanyVisibility visibility) {
        switch (visibility) {
            case Internal:
                deleteInternalMapping(Id);
                break;
            case External:
                deleteExternalMapping(Id);
                break;
        }
         patientInsuranceRepository.deleteById(Id);
        return true;
    }

    private void deleteInternalMapping(Long patientInsuranceId) {
        patientInsuranceInternalCompanyRepository.deleteByPatientInsuranceId(patientInsuranceId);
    }

    private void deleteExternalMapping(Long patientInsuranceId) {
        patientInsuranceExternalCompanyRepository.deleteByExternalPatientInsurance_Id(patientInsuranceId);
    }
}
