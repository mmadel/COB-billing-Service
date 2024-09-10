package com.cob.billing.usecases.bill.tools.modifier.rule;

import com.cob.billing.model.bill.modifier.rule.ModifierRule;
import com.cob.billing.repositories.bill.ModifierRuleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ModifierRuleFinderUseCase {
    @Autowired
    ModifierRuleRepository modifierRuleRepository;
    @Autowired
    ModelMapper mapper;

    public List<ModifierRule> findAll() {
        return StreamSupport.stream(modifierRuleRepository.findAll().spliterator(), false)
                .map(modifierRuleEntity -> mapper.map(modifierRuleEntity, ModifierRule.class)).collect(Collectors.toList());
    }

    public ModifierRule findById(Long id) {
        return mapper.map(modifierRuleRepository.findById(id).get(), ModifierRule.class);
    }
}
