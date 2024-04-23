package com.cob.billing.repositories.bill;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ModifierRuleRepository extends CrudRepository<ModifierRuleEntity, Long> {
    @Query("Select mr from ModifierRuleEntity mr " +
            "where JSON_EXTRACT(mr.insurance, '$.id') =:id " +
            "AND mr.active = true")
    Optional<ModifierRuleEntity> findByInsurance(@Param("id") Long id);
}
