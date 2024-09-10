package com.cob.billing.model.integration.claimmd.era.respose;

import com.cob.billing.model.integration.claimmd.era.PayerClaimMD;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PayerResponse {
    public List<PayerClaimMD> payer;
}
