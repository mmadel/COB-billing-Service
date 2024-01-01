package com.cob.billing.usecases.bill.invoice.cms.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class InsuredInformationModel {
    private String ins_name;
    private String rel_to_ins;
    private String ins_street;
    private String ins_city;
    private String ins_state;
    private String ins_zip;
    private String ins_AreaCode;
    private String ins_phone;
    private String ins_policy;
    private String ins_sex;
    private String ins_dob_mm;
    private String ins_dob_dd;
    private String ins_dob_yy;
    private String other_ins_name;
    private String other_ins_policy;
}
