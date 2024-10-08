package com.cob.billing.model.bill.invoice.response;

import com.cob.billing.enums.ClaimResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClaimSubmission {
    private String claimId;
    private List<String> messages;
    private List<Long> serviceLines;
    private ClaimResponseStatus status;
}
