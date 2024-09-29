package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.modifier.rule.ModifierRuleEntity;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.modifier.rule.Rule;
import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.bill.ModifierRuleRepository;
import com.cob.billing.usecases.bill.tools.modifier.rule.merger.ModifierConverterArray;
import com.cob.billing.usecases.bill.tools.modifier.rule.merger.ModifierMerger;
import com.cob.billing.usecases.clinical.patient.session.UpdatePatientSessionServiceLineUseCase;
import com.itextpdf.styledxmlparser.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CheckModifierRuleUseCase {
    @Autowired
    private ModifierRuleRepository modifierRuleRepository;
    @Autowired
    private UpdatePatientSessionServiceLineUseCase updatePatientSessionServiceLineUseCase;

    @Transactional
    public List<ServiceLine> checkDefault(List<ServiceLine> models) {
        Optional<ModifierRuleEntity> defaultRule = modifierRuleRepository.findDefault();
        if (defaultRule.isPresent()) {
            models.stream().forEach(serviceLine -> {
                CPTCode cptCode = serviceLine.getCptCode();
                List<Rule> rules = getMatchedRules(cptCode.getServiceCode(), defaultRule.get().getRules());
                applyRule(rules, cptCode);
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
                        List<Rule> rule = getMatchedRules(cptCode.getServiceCode(), modifierRuleEntity.orElseThrow().getRules());
                        applyRule(rule, cptCode);
                    });
        }
    }

    private List<Rule> getMatchedRules(String cpt, List<Rule> rules) {
        List<Rule> matchedRules = new ArrayList<>();
        //Check If CPT contains in Default List
        Rule matchedRule = null;
        for (Rule rule : rules) {
            if (rule.getCptCode() != null && rule.getCptCode().equals(cpt)) {
                matchedRule = rule;
                break;
            }
        }
        if (matchedRule != null)
            matchedRules.add(matchedRule);
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
                matchedRules.add(allRule);

        }
        return matchedRules;
    }

    private void applyRule(List<Rule> rules, CPTCode cptCode) {
        rules.forEach(rule -> {
            String[] originalModifier;
            String[] modifiedModifier;
            switch (rule.getAppender()) {
                case replace:
                    replaceModifier(cptCode, rule.getModifier());
                    break;
                case end:
                    originalModifier = cptCode.getModifier().length() != 0 ? ModifierConverterArray.convertModifierToArray(cptCode.getModifier()) : null;
                    modifiedModifier = rule.getModifier().split("\\.");
                    if (originalModifier != null)
                        cptCode.setModifier(StringUtil.join(ModifierMerger.end(originalModifier, modifiedModifier), "."));
                    else
                        replaceModifier(cptCode, rule.getModifier());
                    break;
                case front:
                    originalModifier = cptCode.getModifier().length() != 0 ? ModifierConverterArray.convertModifierToArray(cptCode.getModifier()) : null;
                    modifiedModifier = rule.getModifier().split("\\.");
                    if (originalModifier != null)
                        cptCode.setModifier(StringUtil.join(ModifierMerger.front(originalModifier, modifiedModifier), "."));
                    else
                        replaceModifier(cptCode, rule.getModifier());
                    break;
            }
        });
    }

    private void replaceModifier(CPTCode cptCode, String ruleModifier) {
        cptCode.setModifier(ruleModifier);
    }
}
