package com.cob.billing.repositories.bill.insurance.company;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompanyEntity,Long> {
    @Query("SELECT DISTINCT ice.payerId FROM InsuranceCompanyEntity ice")
    List<Long> findAllDistinctFieldPayerId();
}
