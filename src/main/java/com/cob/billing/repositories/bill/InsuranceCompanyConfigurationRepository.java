package com.cob.billing.repositories.bill;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCompanyConfigurationRepository extends JpaRepository<InsuranceCompanyConfigurationEntity, Long> {
    //InsuranceCompanyConfigurationEntity findByInsuranceCompanyIdentifier(Long identifier);

}
