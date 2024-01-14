package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@Getter
public class FindPatientInsuranceByInsuranceCompanyUseCase {
    @Autowired
    private InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;



    public List<Object> find(List<PatientInsuranceEntity> insurances, String insuranceCompanyName, InsuranceCompanyVisibility visibility) {
        List<Object> result = new ArrayList<>();
        for(int i = 0 ; i <insurances.size();i++ ){
            PatientInsuranceEntity patientInsurance =insurances.get(i);
            switch (visibility) {
                case Internal:
                    if (patientInsurance.getPatientInsuranceInternalCompany() != null
                            && patientInsurance.getPatientInsuranceInternalCompany().getInsuranceCompany().getName().equals(insuranceCompanyName)) {
                        result.add(patientInsurance);
                        result.add(insuranceCompanyConfigurationRepository
                                .findByInternalInsuranceCompany_Id(patientInsurance.getPatientInsuranceInternalCompany()
                                        .getInsuranceCompany().getId()).get());
                    }
                    break;
                case External:
                    if (patientInsurance.getPatientInsuranceExternalCompany() != null
                            && patientInsurance.getPatientInsuranceExternalCompany().getInsuranceCompany().getName().equals(insuranceCompanyName)) {
                        result.add(patientInsurance);
                        result.add(insuranceCompanyConfigurationRepository
                                .findByExternalInsuranceCompany_Id(patientInsurance.getPatientInsuranceExternalCompany()
                                        .getInsuranceCompany().getId()).get());
                    }
                    break;
            }
        }
        return result;
    }
}

