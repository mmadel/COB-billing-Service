package com.cob.billing.repositories.bill;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuranceCompanyConfigurationRepository extends JpaRepository<InsuranceCompanyConfigurationEntity, Long> {

    Optional<InsuranceCompanyConfigurationEntity> findByInternalInsuranceCompany_Id(Long id);
    Optional<InsuranceCompanyConfigurationEntity> findByExternalInsuranceCompany_Id(Long id);

}
