package com.cob.billing.usecases.bill.invoice.cms.rules;

import com.cob.billing.model.bill.invoice.tmp.OtherPatientInsurance;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OtherInsuranceSelectionRules {
    private List<OtherPatientInsurance> otherInsurances;

    public OtherPatientInsurance select(List<OtherPatientInsurance> otherInsurances, String submittedPatientInsuranceResponsibility) throws IllegalAccessException {
        this.otherInsurances = otherInsurances;
        if (otherInsurances.size() == 1)
            return otherInsurances.get(0);
        else {
            Integer submittedResponsibility = checkSubmission(submittedPatientInsuranceResponsibility);
            Integer collection = checkCollection();
            Integer action = OtherPatientInsuranceRuleEngine.fire(submittedResponsibility, collection);
            return pickOtherPatientInsurance(action);
        }
    }

    private OtherPatientInsurance pickOtherPatientInsurance(Integer action) {
        return otherInsurances.stream()
                .collect(Collectors.groupingBy(
                        obj -> obj.getResponsibility().equals("Primary") ? Submission.PRIMARY : Submission.SECONDARY,
                        Collectors.minBy(Comparator.comparingLong(OtherPatientInsurance::getCreatedAt))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().orElse(null)
                )).get(action);
    }

    private Integer checkSubmission(String submittedPatientInsuranceResponsibility) throws IllegalAccessException {
        switch (submittedPatientInsuranceResponsibility) {
            case "Primary":
                return Submission.PRIMARY;
            case "Secondary":
                return Submission.SECONDARY;
            default:
                throw new IllegalAccessException("Illegal Submitted PatientInsurance Responsibility");
        }
    }

    private Integer checkCollection() {
        boolean allPrimary = otherInsurances.stream().allMatch(patientInsurance -> patientInsurance.getResponsibility().equals("Primary"));
        boolean allSecondary = otherInsurances.stream().allMatch(patientInsurance -> patientInsurance.getResponsibility().equals("Secondary"));
        if (allPrimary)
            return Collection.ALL_PRIMARY;
        if (allSecondary)
            return Collection.ALL_SECONDARY;
        return Collection.PRIMARY_SECONDARY;
    }
}
