package com.cob.billing.model.integration.claimmd.era;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class PayerClaimMD {
    public String payer_state;
    public String auto;
    @JsonProperty("1500_claims")
    public String _1500_claims;
    public String workers_comp;
    public String payerid;
    public String ub_claims;
    public String secondary_support;
    public String payer_name;
    public String dent_claims;
    public int avg_era_enroll_days;
    public String attachment;
    public String eligibility;
    public ArrayList<PayerAltName> payer_alt_names;
    public String payer_type;
    public String era;
}
