package com.cob.billing.usecases.bill.tools.modifier.rule;

import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.usecases.clinical.patient.insurance.company.FindInsuranceCompaniesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindModifierRuleMetaDataUseCase {
    @Autowired
    FindInsuranceCompaniesUseCase findInsuranceCompaniesUseCase;
    public List<InsuranceCompanyHolder> findInsuranceCompany(){
        return findInsuranceCompaniesUseCase.find();
    }
}
