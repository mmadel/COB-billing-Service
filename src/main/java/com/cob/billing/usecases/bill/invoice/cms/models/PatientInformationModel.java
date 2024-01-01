package com.cob.billing.usecases.bill.invoice.cms.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PatientInformationModel {
    private String pt_name;
    private String birth_mm;
    private String birth_dd;
    private String birth_yy;
    private String sex;
    private String pt_street;
    private String pt_city;
    private String pt_state;
    private String pt_zip;
    private String pt_AreaCode;
    private String pt_phone;
    private String  employment;
    private String pt_auto_accident;
    private String other_accident;
    private String _40;
    private String accident_place;




}
