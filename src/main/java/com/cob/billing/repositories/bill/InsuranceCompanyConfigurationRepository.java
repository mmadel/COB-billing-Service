package com.cob.billing.repositories.bill;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCompanyConfigurationRepository  extends JpaRepository<InsuranceCompanyConfigurationEntity, Long> {
}
