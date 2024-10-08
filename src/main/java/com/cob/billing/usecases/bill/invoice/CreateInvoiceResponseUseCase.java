package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.enums.ClaimResponseStatus;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.response.ClaimSubmission;
import com.cob.billing.model.bill.invoice.response.InvoiceResponse;
import com.cob.billing.model.integration.claimmd.submit.SubmitResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateInvoiceResponseUseCase {
    public void create(InvoiceResponse invoiceResponse, SubmitResponse submitResponse) {
        List<ClaimSubmission> claimSubmissions = submitResponse.getClaim().stream()
                .map(claimResponse -> {
                    ClaimSubmission claimSubmission = new ClaimSubmission();
                    claimSubmission.setClaimId(claimResponse.getClaimmd_id());
                    switch (claimResponse.getStatus()) {
                        case "R":
                            claimSubmission.setStatus(ClaimResponseStatus.Claim_Rejection);
                            break;
                        case "A":
                            claimSubmission.setStatus(ClaimResponseStatus.Claim_Acknowledgment);
                            break;
                    }
                    claimSubmission.setMessages(claimResponse.getMessages().stream()
                            .map(messageResponse -> messageResponse.getMessage())
                            .collect(Collectors.toList()));
                    List<Long> serviceLines = Arrays.stream(claimResponse.getRemote_claimid().split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                    claimSubmission.setServiceLines(serviceLines);
                    return claimSubmission;
                }).collect(Collectors.toList());
        invoiceResponse.setClaimSubmissions(claimSubmissions);
    }
    public  void create(List<ClaimSubmission> claimSubmissions , List<SelectedSessionServiceLine> serviceLines){
        ClaimSubmission claimSubmission = new ClaimSubmission();
        claimSubmission.setServiceLines(serviceLines.stream()
                .map(serviceLine -> serviceLine.getServiceLine().getId()).collect(Collectors.toList()));
        claimSubmission.setMessages(Arrays.asList("Success"));
        Long claimId = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        claimSubmission.setClaimId(Long.toString(claimId));
        claimSubmission.setStatus(ClaimResponseStatus.Claim_Success);
        claimSubmissions.add(claimSubmission);
    }
}
