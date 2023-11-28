package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CPTCode {
    private String serviceCode;
    private String modifier;
    private Float unit;
    private Integer charge;
}
