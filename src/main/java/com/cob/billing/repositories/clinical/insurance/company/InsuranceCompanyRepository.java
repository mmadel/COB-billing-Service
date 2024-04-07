package com.cob.billing.repositories.clinical.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompanyEntity,Long> {
    @Query("SELECT ic FROM InsuranceCompanyEntity ic where " +
            "(:name is null or upper(ic.name) LIKE CONCAT('%',:name,'%'))")
    Optional<List<InsuranceCompanyEntity>> findByInsuranceCompanyName(@Param("name") String name);
}
