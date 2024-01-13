package com.cob.billing.usecases.clinical.patient.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyPayerEntity;
import com.cob.billing.model.bill.InsuranceCompanyMapper;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyPayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapInsuranceCompanyToPayerUseCase {
    @Autowired
    InsuranceCompanyPayerRepository insuranceCompanyPayerRepository;

    public void map(InsuranceCompanyMapper companyMapper) {
        InsuranceCompanyEntity insuranceCompany = new InsuranceCompanyEntity();
        insuranceCompany.setId(companyMapper.getInsuranceCompanyId());
        InsuranceCompanyPayerEntity insuranceCompanyPayer = new InsuranceCompanyPayerEntity();
        insuranceCompanyPayer.setId(companyMapper.getId());
        insuranceCompanyPayer.setInternalInsuranceCompany(insuranceCompany);
        insuranceCompanyPayer.setPayerId(companyMapper.getPayer().getPayerId());
        insuranceCompanyPayer.setPayer(companyMapper.getPayer());
        insuranceCompanyPayerRepository.save(insuranceCompanyPayer);
    }

}
