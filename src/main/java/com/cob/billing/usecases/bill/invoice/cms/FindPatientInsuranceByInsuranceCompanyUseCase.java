package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
@Getter
public class FindPatientInsuranceByInsuranceCompanyUseCase {
    @Autowired
    private InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;

    private PatientInsuranceEntity patientInsuranceCompany;
    private InsuranceCompanyConfigurationEntity insuranceCompanyConfiguration;

    public void find(List<PatientInsuranceEntity> insurances, String insuranceCompanyName, InsuranceCompanyVisibility visibility) {

        insurances.stream()
                .forEach(patientInsurance -> {
                    if (patientInsuranceCompany == null)
                        switch (visibility) {
                            case Internal:
                                if (patientInsurance.getPatientInsuranceInternalCompany() != null
                                        && patientInsurance.getPatientInsuranceInternalCompany().getInsuranceCompany().getName().equals(insuranceCompanyName)) {
                                    patientInsuranceCompany = patientInsurance;
                                    insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository
                                            .findByInternalInsuranceCompany_Id(patientInsurance.getPatientInsuranceInternalCompany()
                                                    .getInsuranceCompany().getId()).get();
                                }
                                break;
                            case External:
                                if (patientInsurance.getPatientInsuranceExternalCompany() != null
                                        && patientInsurance.getPatientInsuranceExternalCompany().getInsuranceCompany().getName().equals(insuranceCompanyName)) {
                                    patientInsuranceCompany = patientInsurance;
                                    insuranceCompanyConfiguration = insuranceCompanyConfigurationRepository
                                            .findByExternalInsuranceCompany_Id(patientInsurance.getPatientInsuranceExternalCompany()
                                                    .getInsuranceCompany().getId()).get();
                                }
                                break;
                        }
                });
    }
}

