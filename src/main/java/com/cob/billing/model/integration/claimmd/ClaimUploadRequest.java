package com.cob.billing.model.integration.claimmd;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@Builder
public class ClaimUploadRequest implements Serializable {
    public String fileid;
    public List<Claim> claim;
}
