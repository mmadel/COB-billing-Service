package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ICD10DiagnosisResponse {
    private Double countOfResult;
    private List<String> listOfCode;
    private List<List<String>> listOfCodeName;
}
