package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientCase {

    private Long id;
    private String title;
    private List<CaseDiagnosis> caseDiagnosis;
}
