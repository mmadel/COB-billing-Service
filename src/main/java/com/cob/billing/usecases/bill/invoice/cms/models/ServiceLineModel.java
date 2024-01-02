package com.cob.billing.usecases.bill.invoice.cms.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ServiceLineModel {
    private String sv_mm_from;
    private String sv_dd_from;
    private String sv_yy_from;

    private String sv_mm_end;
    private String sv_dd_end;
    private String sv_yy_end;

    //charge
    private String ch;
    //Unit
    private String day;
    // provider npi
    private String local;

    private String[] mod;

    private String cpt;
    //mapping cpt to icd10
    private String diag;

    //place of code
    private String place;
}
