package com.cob.billing.model.clinical.patient.session;

import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ServiceLine {
    private Long id;
    private CPTCode cptCode;
    private String type;
    private List<String> diagnoses;
    private Boolean isCorrect;
    private String lineNote;
    private double payments;
    private Boolean isChanged;
}
