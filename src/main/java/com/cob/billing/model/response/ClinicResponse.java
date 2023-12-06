package com.cob.billing.model.response;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.clinical.patient.Patient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ClinicResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<Clinic> records;
}
