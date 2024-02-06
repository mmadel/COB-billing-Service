package com.cob.billing.model.integration.claimmd;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Charge {
    public String charge;
    public String charge_record_type;
    public String diag_ref;
    public String from_date;
    public String place_of_service;
    public String proc_code;
    public String remote_chgid;
    public String thru_date;
    public String units;
    public String mod1;
}
