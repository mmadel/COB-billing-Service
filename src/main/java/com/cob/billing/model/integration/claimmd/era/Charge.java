package com.cob.billing.model.integration.claimmd.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Charge {
    private String charge;
    private Object mod4;
    private Object thru_dos;
    private String units;
    private Object mod3;
    private Object pos;
    private String from_dos;
    private String proc_code;
    private List<Adjustment> adjustment;
    private String allowed;
    private String mod2;
    private String mod1;
    private String paid;
    private String chgid;
}
