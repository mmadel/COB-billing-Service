package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.bill.ModifierRuleRepository;
import com.cob.billing.usecases.bill.tools.modifier.rule.util.ListShiftUtil;
import com.cob.billing.usecases.clinical.patient.session.UpdatePatientSessionServiceLineUseCase;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InvoiceModifierRuleUseCase {
    @Autowired
    ModifierRuleRepository modifierRuleRepository;
    ModifierRuleEntity modifierRule;
    @Autowired
    UpdatePatientSessionServiceLineUseCase updatePatientSessionServiceLineUseCase;

    @Transactional
    public List<ServiceLine> checkDefault(List<ServiceLine> models) {
        Optional<ModifierRuleEntity> rule = modifierRuleRepository.findDefault();
        if (rule.isPresent())
            modifierRule = rule.get();
        List<CPTCode> cptCodes = models.stream()
                .map(ServiceLine::getCptCode).collect(Collectors.toList());
        change(cptCodes);
        updatePatientSessionServiceLineUseCase.update(models);
        return models;
    }

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
                boolean forntContains = modifiedModifier.stream()
                        .anyMatch(originalModifier::contains);
                if (!forntContains)
                    code.setModifier(shiftModifierLeft(originalModifier, modifiedModifier));
                break;
            case end:
                boolean endContains = modifiedModifier.stream()
                        .anyMatch(originalModifier::contains);
                if (!endContains)
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
