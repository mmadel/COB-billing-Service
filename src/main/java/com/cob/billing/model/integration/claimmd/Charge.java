package com.cob.billing.model.integration.claimmd;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Charge implements Serializable {
    private String charge;
    private String charge_record_type;
    private String diag_ref;
    private String from_date;
    private String place_of_service;
    private String proc_code;
    private String remote_chgid;
    private String thru_date;
    private String units;
    private String mod1;
    private String mod2;
    private String mod3;
    private String mod4;
    private String chg_prov_npi;
    private String chg_prov_id;
    private String chg_prov_taxonomy;
}
