package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.model.bill.invoice.SubmissionClaimMessages;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FindSubmissionClaimsMessagesUseCase {
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;

    public List<SubmissionClaimMessages> find(Long submissionId) {
        List<PatientSubmittedClaim> claims = patientSubmittedClaimRepository.findBySubmissionId(submissionId);
        List<SubmissionClaimMessages> submissionClaimMessages = new ArrayList<>();
        if (claims != null)
            claims.forEach(patientSubmittedClaim -> {
                SubmissionClaimMessages claimMessages = SubmissionClaimMessages.builder()
                        .remoteClaimId(patientSubmittedClaim.getRemoteClaimId())
                        .dos(patientSubmittedClaim.getPatientSession().getServiceDate())
                        .messages(groupMessages(patientSubmittedClaim.getMessages()))
                        .build();
                submissionClaimMessages.add(claimMessages);
            });
        return submissionClaimMessages;
    }

    private List<String> groupMessages(List<String> messages) {
        // Define patterns to group messages
        Map<String, Pattern> categories = new HashMap<>();
        categories.put("Date Issues", Pattern.compile(".*date.*", Pattern.CASE_INSENSITIVE));
        categories.put("State Specific", Pattern.compile(".*payer.*state.*", Pattern.CASE_INSENSITIVE));
        categories.put("NCCI Issues", Pattern.compile(".*NCCI.*", Pattern.CASE_INSENSITIVE));
        categories.put("Sex Issues", Pattern.compile(".*Sex.*", Pattern.CASE_INSENSITIVE));
        categories.put("License Issues", Pattern.compile(".*License.*", Pattern.CASE_INSENSITIVE));
        categories.put("Other", Pattern.compile(".*"));

        // Group the messages and remove duplicates using a Set
        return messages.stream()
                .collect(Collectors.groupingBy(message -> categories.entrySet().stream()
                        .filter(entry -> entry.getValue().matcher(message).matches())
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("Other")))
                .entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()) // Flatten grouped messages
                .distinct() // Remove duplicates
                .collect(Collectors.toList());
    }
}
