package com.cob.billing.model.integration.claimmd.submit;

import com.cob.billing.model.integration.claimmd.Claim;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Builder
public class SubmitRequest implements Serializable {
    public String fileid;
    public List<Claim> claim;
}
