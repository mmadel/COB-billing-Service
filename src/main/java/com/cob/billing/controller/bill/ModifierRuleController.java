package com.cob.billing.controller.bill;

import com.cob.billing.model.bill.modifier.rule.ModifierRuleModel;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.usecases.bill.invoice.CheckModifierRuleUseCase;
import com.cob.billing.usecases.bill.tools.modifier.rule.CreateModifierRuleUseCase;
import com.cob.billing.usecases.bill.tools.modifier.rule.FindModifierRuleMetaDataUseCase;
import com.cob.billing.usecases.bill.tools.modifier.rule.ModifierRuleFinderUseCase;
import com.cob.billing.usecases.bill.tools.modifier.rule.RemoveModifierRuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/modifier")
@PreAuthorize("hasAnyRole('billing-role','modifier-rule-billing-role')")
public class ModifierRuleController {
    @Autowired
    CreateModifierRuleUseCase createModifierRuleUseCase;
    @Autowired
    ModifierRuleFinderUseCase modifierRuleFinderUseCase;
    @Autowired
    RemoveModifierRuleUseCase removeModifierRuleUseCase;
    @Autowired
    FindModifierRuleMetaDataUseCase findModifierRuleMetaDataUseCase;
    @Autowired
    CheckModifierRuleUseCase checkModifierRuleUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ModifierRuleModel model) {
        return new ResponseEntity(createModifierRuleUseCase.create(model), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity find() {

        return new ResponseEntity(modifierRuleFinderUseCase.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return new ResponseEntity(modifierRuleFinderUseCase.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(removeModifierRuleUseCase.removeById(id), HttpStatus.OK);
    }

    @GetMapping("/meta-data/insurance-companies")
    public ResponseEntity findMetaDara() {
        return new ResponseEntity(findModifierRuleMetaDataUseCase.findInsuranceCompany(), HttpStatus.OK);
    }

    @PostMapping("/fire-default")
    public ResponseEntity fireDefaultModifierRule(@RequestBody List<ServiceLine> models) {;
        return new ResponseEntity(checkModifierRuleUseCase.checkDefault(models), HttpStatus.OK);
    }
}
