package com.cob.billing.repositories.bill.insurance.company;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompanyEntity,Long> {
}
