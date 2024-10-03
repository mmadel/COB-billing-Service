package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.model.bill.invoice.SubmissionClaimMessages;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindSubmissionClaimsMessagesUseCase {
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;

    public List<SubmissionClaimMessages> find(Long submissionId) {
        List<PatientSubmittedClaim> claims = patientSubmittedClaimRepository.findBySubmissionId(submissionId);
        List<SubmissionClaimMessages> submissionClaimMessages = new ArrayList<>();
        claims.forEach(patientSubmittedClaim -> {
            SubmissionClaimMessages claimMessages = SubmissionClaimMessages.builder()
                    .remoteClaimId(patientSubmittedClaim.getRemoteClaimId())
                    .dos(patientSubmittedClaim.getPatientSession().getServiceDate())
                    .messages(patientSubmittedClaim.getMessages())
                    .build();
            submissionClaimMessages.add(claimMessages);
        });
        return submissionClaimMessages;
    }
}
