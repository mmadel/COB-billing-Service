package com.cob.billing.repositories.bill;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import org.springframework.data.repository.CrudRepository;

public interface ModifierRuleRepository extends CrudRepository<ModifierRuleEntity, Long> {
}
