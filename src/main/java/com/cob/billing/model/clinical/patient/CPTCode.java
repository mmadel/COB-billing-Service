package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CPTCode {
    private String serviceCode;
    private String modifier;
    private Integer unit;
    private double charge;
}
