package com.cob.billing.repositories.clinical.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompanyEntity,Long> {
    @Query("SELECT ic FROM InsuranceCompanyEntity ic where " +
            "ic.name =:name")
    Optional<InsuranceCompanyEntity> findByInsuranceCompanyName(@Param("name") String name);
}
