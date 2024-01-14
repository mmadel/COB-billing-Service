package com.cob.billing.repositories.clinical.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyPayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InsuranceCompanyPayerRepository extends JpaRepository<InsuranceCompanyPayerEntity,Long> {
    @Query("SELECT icp from InsuranceCompanyPayerEntity icp where icp.internalInsuranceCompany.id in :ids")
    List<InsuranceCompanyPayerEntity> findByInternalInsuranceCompanies(List<Long> ids);
    Optional<InsuranceCompanyPayerEntity> findByInternalInsuranceCompany_Id(Long id);
}
