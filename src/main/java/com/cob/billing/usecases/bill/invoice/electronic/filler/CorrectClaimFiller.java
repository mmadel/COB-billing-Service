package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.tmp.CorrectClaimInformation;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

@Component
public class CorrectClaimFiller {
    public void fill(CorrectClaimInformation correctClaimInformation , Claim claim){
        claim.setFrequency_code(correctClaimInformation.getResubmissionCode());
        claim.setIcn_dcn_1(correctClaimInformation.getRefNumber());
    }
}
