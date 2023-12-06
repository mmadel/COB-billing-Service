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
    private List<CaseDiagnosis> caseDiagnosis;
    private CPTCode cptCode;
    private String type;
}
