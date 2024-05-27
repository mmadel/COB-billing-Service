package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateInternalInsuranceCompanyUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    public void update(InsuranceCompanyHolder insuranceCompanyHolder) {
        InsuranceCompanyEntity entity = insuranceCompanyRepository.findById(insuranceCompanyHolder.getId()).get();
        map(entity, insuranceCompanyHolder);
        insuranceCompanyRepository.save(entity);
    }

    private void map(InsuranceCompanyEntity entity, InsuranceCompanyHolder insuranceCompanyHolder) {
        entity.setName(insuranceCompanyHolder.getName());
        entity.setAddress(insuranceCompanyHolder.getAddress());
    }
}
