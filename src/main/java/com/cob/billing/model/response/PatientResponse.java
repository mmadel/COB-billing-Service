package com.cob.billing.model.response;

import com.cob.billing.model.clinical.patient.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<Patient> records;
}
