package com.cob.billing.usecases.bill.tools.modifier.rule;

import com.cob.billing.repositories.bill.ModifierRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveModifierRuleUseCase {
    @Autowired
    ModifierRuleRepository modifierRuleRepository;
    public Long removeById(Long id){
        modifierRuleRepository.deleteById(id);
        return id;
    }
}
