package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.repositories.bill.ModifierRuleRepository;
import com.cob.billing.usecases.bill.tools.modifier.rule.util.ListShiftUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InvoiceModifierRuleUseCase {
    @Autowired
    ModifierRuleRepository modifierRuleRepository;
    ModifierRuleEntity modifierRule;

    public void check(List<CPTCode> cptCode, Long insuranceId) {
        findModifierRule(insuranceId);
        change(cptCode);
    }

    private void findModifierRule(Long insuranceId) {
        Optional<ModifierRuleEntity> rule = modifierRuleRepository.findByInsurance(insuranceId);
        if (rule.isPresent())
            modifierRule = rule.get();
        else
            modifierRule = null;
    }

    private void change(List<CPTCode> cptCode) {
        if (modifierRule != null) {
            cptCode.forEach(code -> {
                List<String> originalModifier = new ArrayList<>(Arrays.asList(code.getModifier().split("\\.")));
                List<String> modifiedModifier = new ArrayList<>(Arrays.asList(modifierRule.getModifier().split("\\.")));
                if (modifierRule.getCptCode() != null && !(modifierRule.getCptCode().isEmpty())) {
                    if (code.getServiceCode().equals(modifierRule.getCptCode())) {
                        execute(code, originalModifier, modifiedModifier);
                    }
                } else {
                    execute(code, originalModifier, modifiedModifier);
                }

            });
        }
    }

    private void execute(CPTCode code, List<String> originalModifier, List<String> modifiedModifier) {
        switch (modifierRule.getAppender()) {
            case replace:
                replaceModifier(code, modifierRule.getModifier());
                break;
            case front:
                code.setModifier(shiftModifierLeft(originalModifier, modifiedModifier));
                break;
            case end:
                code.setModifier(shiftModifierRight(originalModifier, modifiedModifier));
                break;
        }
    }

    private void replaceModifier(CPTCode cptCode, String ruleModifier) {
        cptCode.setModifier(ruleModifier);
    }

    private String shiftModifierLeft(List<String> originalModifier, List<String> modifiedModifier) {
        List<String> updated = ListShiftUtil.leftShift(originalModifier, modifiedModifier);
        return StringUtils.join(updated, '.');
    }

    private String shiftModifierRight(List<String> originalModifier, List<String> modifiedModifier) {
        List<String> updated = ListShiftUtil.rightShift(originalModifier, modifiedModifier);
        return StringUtils.join(updated, '.');
    }
}
