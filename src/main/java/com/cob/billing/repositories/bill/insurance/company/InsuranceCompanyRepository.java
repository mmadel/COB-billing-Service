package com.cob.billing.repositories.bill.insurance.company;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompanyEntity,Long> {
    @Query("SELECT DISTINCT ice.payerId FROM InsuranceCompanyEntity ice")
    List<Long> findAllDistinctFieldPayerId();
    InsuranceCompanyEntity findByPayerId(Long payerId);

    @Query("SELECT ic FROM InsuranceCompanyEntity ic where " +
            "ic.name LIKE %:name%")
    InsuranceCompanyEntity findByInsuranceCompanyName(@Param("name") String name);
}
