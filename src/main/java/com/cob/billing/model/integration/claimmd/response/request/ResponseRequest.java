package com.cob.billing.model.integration.claimmd.response.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseRequest {
    private Long last_responseid;
    List<ClaimResponse> claim;
    private List<String> error;
}
