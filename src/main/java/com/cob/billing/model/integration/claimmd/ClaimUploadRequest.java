package com.cob.billing.model.integration.claimmd;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClaimUploadRequest {
    public String fileid;
    public List<Claim> claim;
}
