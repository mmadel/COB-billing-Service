package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.model.integration.claimmd.ClaimResponse;
import com.cob.billing.model.integration.claimmd.status.updates.StatusUpdateResponse;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.usecases.integration.claim.md.CacheClaimMDResponseDataUseCase;
import com.cob.billing.usecases.integration.claim.md.RetrieveClaimsHistoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateSubmittedClaimStatus {
    @Autowired
    RetrieveClaimsHistoryUseCase retrieveClaimsHistoryUseCase;
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;
    @Autowired
    CacheClaimMDResponseDataUseCase cacheClaimMDResponseDataUseCase;


    @Transactional
    public void update() {
        List<Long> updatesClaim = new ArrayList<>();
        List<PatientSubmittedClaim> pendingSubmittedClaims = patientSubmittedClaimRepository.findBySubmissionStatus(SubmissionStatus.Pending);
        StatusUpdateResponse statusUpdateResponse = retrieveClaimsHistoryUseCase.get(cacheClaimMDResponseDataUseCase.getCachedNumber());
        if (statusUpdateResponse.getLast_responseid() != null)
            cacheClaimMDResponseDataUseCase.updateCachedNumber(Long.parseLong(statusUpdateResponse.getLast_responseid()));
        if (pendingSubmittedClaims != null && !pendingSubmittedClaims.isEmpty()) {
            for (PatientSubmittedClaim submittedClaim : pendingSubmittedClaims) {

                for (ClaimResponse claimResponse : statusUpdateResponse.getClaim()) {
                    if (claimResponse.getClaimmd_id().equals(Long.toString(submittedClaim.getRemoteClaimId()))
                            && claimResponse.getSender_name().toLowerCase().equals(submittedClaim.getPatientInvoiceRecord().getInsuranceCompanyName().toLowerCase())) {
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
