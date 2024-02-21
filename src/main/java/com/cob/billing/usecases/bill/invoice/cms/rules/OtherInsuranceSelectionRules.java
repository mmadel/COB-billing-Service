package com.cob.billing.usecases.bill.invoice.cms.rules;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtherInsuranceSelectionRules {
    private List<String[]> otherInsurances;
    public String[] select(List<String[]> otherInsurances){
        this.otherInsurances = otherInsurances;
        if(otherInsurances.size()==1)
            return new String[]{otherInsurances.get(0)[0],
                    otherInsurances.get(0)[1],otherInsurances.get(0)[2]};
        return null;
    }
}
