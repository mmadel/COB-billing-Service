package com.cob.billing.model.clinical.patient.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PatientSessionServiceLine {
    private Long dos;
    private String provider;
    private String caseTitle;
    private String place;
    private String cpt;
    private Integer unit;
    private double charge;
    private double payments;
    private PatientSession data;
    private Long cptId;
    private ServiceLine serviceCode;
    private Boolean isCorrect;
}
