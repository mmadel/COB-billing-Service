package com.cob.billing.jobs;

import com.cob.billing.usecases.bill.invoice.UpdateSubmittedClaimStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateSubmittedClaimsStatusJob {
    @Autowired
    UpdateSubmittedClaimStatus updateSubmittedClaimStatus;

    @Scheduled(cron = "${update_claim_cron_job}")
    public void callUpdateSubmittedStatus() {
        System.out.println("callUpdateSubmittedStatus ");
        updateSubmittedClaimStatus.update();
    }
}
