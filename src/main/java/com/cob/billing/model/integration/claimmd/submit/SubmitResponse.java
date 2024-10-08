package com.cob.billing.model.integration.claimmd.submit;

import com.cob.billing.model.integration.claimmd.ClaimResponse;
import com.cob.billing.model.integration.claimmd.ErrorClaim;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SubmitResponse {
    private String messages;
    List<ClaimResponse> claim;
    List<ErrorClaim> error;
}
