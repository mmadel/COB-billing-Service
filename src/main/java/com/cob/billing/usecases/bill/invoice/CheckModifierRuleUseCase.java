package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.modifier.rule.Rule;
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
public class CheckModifierRuleUseCase {
    @Autowired
    ModifierRuleRepository modifierRuleRepository;
    ModifierRuleEntity modifierRule;
    @Autowired
    UpdatePatientSessionServiceLineUseCase updatePatientSessionServiceLineUseCase;

    @Transactional
    public List<ServiceLine> checkDefault(List<ServiceLine> models) {
        Optional<ModifierRuleEntity> defaultRule = modifierRuleRepository.findDefault();
        if (defaultRule.isPresent()) {
            models.stream().forEach(serviceLine -> {
                CPTCode cptCode = serviceLine.getCptCode();
                Rule rule = getRuleByCPT(cptCode.getServiceCode(), defaultRule.get().getRules());
                applyRule(rule, cptCode);
            });
        }
        updatePatientSessionServiceLineUseCase.update(models);
        return models;
    }

    public void check(InvoiceRequest invoiceRequest) {
        Optional<ModifierRuleEntity> modifierRuleEntity = modifierRuleRepository.findByInsuranceCompanyId(invoiceRequest.getInvoiceInsuranceCompanyInformation().getId().toString());
        if (modifierRuleEntity.isPresent()) {
            invoiceRequest.getSelectedSessionServiceLine().stream()
                    .forEach(serviceLine -> {
                        CPTCode cptCode = serviceLine.getServiceLine().getCptCode();
                        Rule rule = getRuleByCPT(cptCode.getServiceCode(), modifierRuleEntity.orElseThrow().getRules());
                        applyRule(rule, cptCode);
                    });
        }
    }

    private Rule getRuleByCPT(String cpt, List<Rule> rules) {
        //Check If CPT contains in Default List
        Rule matchedRule = null;
        for (Rule rule : rules) {
            if (rule.getCptCode() != null && rule.getCptCode().equals(cpt)) {
                matchedRule = rule;
                break;
            }
        }
        if (matchedRule != null)
            return matchedRule;
        else {
            //Check All CPT
            Rule allRule = null;
            for (Rule rule : rules) {
                if (rule.getCptCode() == null) {
                    allRule = rule;
                    break;
                }
            }
            if (allRule != null)
                return allRule;
            else
                return null;
        }
    }

    private void getRuleByInsuranceCompany(String insuranceCompanyId) {
        Optional<ModifierRuleEntity> modifierRuleEntity = modifierRuleRepository.findByInsuranceCompanyId(insuranceCompanyId);

    }

    private void applyRule(Rule rule, CPTCode cptCode) {
        List<String> originalModifier;
        List<String> modifiedModifier;
        switch (rule.getAppender()) {
            case replace:
                replaceModifier(cptCode, rule.getModifier());
                break;
            case end:
                originalModifier = cptCode.getModifier().length() != 0 ? new ArrayList<>(Arrays.asList(cptCode.getModifier().split("\\."))) : null;
                modifiedModifier = new ArrayList<>(Arrays.asList(rule.getModifier().split("\\.")));
                if (originalModifier != null)
                    cptCode.setModifier(shiftModifierRight(originalModifier, modifiedModifier));
                else
                    replaceModifier(cptCode, rule.getModifier());
                break;
            case front:
                originalModifier = cptCode.getModifier().length() != 0 ? new ArrayList<>(Arrays.asList(cptCode.getModifier().split("\\."))) : null;
                modifiedModifier = new ArrayList<>(Arrays.asList(rule.getModifier().split("\\.")));
                if (originalModifier != null)
                    cptCode.setModifier(shiftModifierLeft(originalModifier, modifiedModifier));
                else
                    replaceModifier(cptCode, rule.getModifier());
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
