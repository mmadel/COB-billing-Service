package com.cob.billing.usecases.bill.invoice.claim;

import com.cob.billing.enums.SubmissionType;
import com.cob.billing.util.BeanFactory;

public class ClaimCreator {
    public static BillingClaim getInstance(SubmissionType submissionType){
        switch (submissionType){
            case Print:
                return BeanFactory.getBean(PrintableBillingClaim.class);
            case Electronic:
                return BeanFactory.getBean(ElectronicBillingClaim.class);
            default:
                throw new IllegalArgumentException("Unknown Submission Type");
        }
    }
}
