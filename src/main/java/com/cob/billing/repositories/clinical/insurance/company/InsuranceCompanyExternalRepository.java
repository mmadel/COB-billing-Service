package com.cob.billing.repositories.clinical.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyExternalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InsuranceCompanyExternalRepository extends JpaRepository<InsuranceCompanyExternalEntity,Long> {
    @Query("SELECT eic FROM InsuranceCompanyExternalEntity eic where " +
            "eic.name =:name")
    Optional<InsuranceCompanyExternalEntity> findByInsuranceCompanyName(@Param("name") String name);
}
