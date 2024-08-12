package com.cob.billing.model.integration.claimmd;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ClaimUploadRequest implements Serializable {
    public String fileid;
    public List<Claim> claim;
}
