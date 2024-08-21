package com.cob.billing.model.integration.claimmd.status.updates;

import com.cob.billing.model.integration.claimmd.ClaimResponse;
import com.cob.billing.model.integration.claimmd.ErrorClaim;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StatusUpdateResponse {
    private String last_responseid;
    List<ClaimResponse> claim;
    List<ErrorClaim> error;
}
