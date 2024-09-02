package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.model.integration.claimmd.ClaimResponse;
import com.cob.billing.model.integration.claimmd.status.updates.StatusUpdateResponse;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.usecases.integration.claim.md.CacheResponseIdUseCase;
import com.cob.billing.usecases.integration.claim.md.GetClaimsHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateSubmittedClaimStatus {
    @Autowired
    GetClaimsHistoryUseCase getClaimsHistoryUseCase;
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;
    @Autowired
    CacheResponseIdUseCase cacheResponseIdUseCase;


    @Transactional
    public void update() {
        List<Long> updatesClaim = new ArrayList<>();
        List<PatientSubmittedClaim> pendingSubmittedClaims = patientSubmittedClaimRepository.findBySubmissionStatus(SubmissionStatus.Pending);
        StatusUpdateResponse statusUpdateResponse = getClaimsHistoryUseCase.get(cacheResponseIdUseCase.getCachedNumber());
        if (statusUpdateResponse.getLast_responseid() != null)
            cacheResponseIdUseCase.updateCachedNumber(Long.parseLong(statusUpdateResponse.getLast_responseid()));
        if (pendingSubmittedClaims != null && !pendingSubmittedClaims.isEmpty()) {
            for (PatientSubmittedClaim submittedClaim : pendingSubmittedClaims) {

                for (ClaimResponse claimResponse : statusUpdateResponse.getClaim()) {
                    if (claimResponse.getClaimmd_id().equals(Long.toString(submittedClaim.getRemoteClaimId()))
                            && claimResponse.getSender_name().equals(submittedClaim.getPatientInvoiceRecord().getInsuranceCompanyName())) {
                        updatesClaim.add(submittedClaim.getId());
                    }
                }
            }
            List<PatientSubmittedClaim> toBeUpdated = patientSubmittedClaimRepository.test(updatesClaim);
            toBeUpdated.stream()
                    .forEach(patientSubmittedClaim -> patientSubmittedClaim.setSubmissionStatus(SubmissionStatus.acknowledge));
            if (!toBeUpdated.isEmpty())
                patientSubmittedClaimRepository.saveAll(toBeUpdated);
        }
    }
}
