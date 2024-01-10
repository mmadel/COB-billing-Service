package com.cob.billing.usecases.bill;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.repositories.bill.InsuranceCompanyConfigurationRepository;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindInsuranceCompaniesUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    InsuranceCompanyConfigurationRepository insuranceCompanyConfigurationRepository;
    @Autowired
    PayerRepository payerRepository;

    public void find() {

        List<InsuranceCompanyEntity> entities = insuranceCompanyRepository.findAll();
        insuranceCompanyConfigurationRepository.findAll();
    }
}
