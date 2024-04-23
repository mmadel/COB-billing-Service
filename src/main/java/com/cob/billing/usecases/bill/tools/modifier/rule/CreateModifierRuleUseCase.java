package com.cob.billing.usecases.bill.tools.modifier.rule;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import com.cob.billing.model.bill.modifier.rule.ModifierRuleModel;
import com.cob.billing.repositories.bill.ModifierRuleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateModifierRuleUseCase {
    @Autowired
    ModifierRuleRepository modifierRuleRepository;
    @Autowired
    ModelMapper mapper;

    @Transactional
    public Long create(ModifierRuleModel model) {
        ModifierRuleEntity toBeCreated = mapper.map(model, ModifierRuleEntity.class);
        return modifierRuleRepository.save(toBeCreated).getId();
    }
}
