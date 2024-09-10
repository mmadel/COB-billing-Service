package com.cob.billing.repositories.bill;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ModifierRuleRepository extends CrudRepository<ModifierRuleEntity, Long> {

//    Optional<ModifierRuleEntity> findByInsuranceCompanyId(@Param("id") Long id);

    @Query("Select mr from ModifierRuleEntity mr where mr.defaultRule =true")
    Optional<ModifierRuleEntity> findDefault();
}
