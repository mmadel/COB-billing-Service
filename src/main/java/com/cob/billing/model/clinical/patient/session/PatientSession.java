package com.cob.billing.model.clinical.patient.session;

import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientSession {
    private Long id;
    private PatientInfo patientInfo;
    private DoctorInfo doctorInfo;
    private ClinicInfo clinicInfo;
    private Long serviceDate;
    private Long serviceStartTime;
    private Long serviceEndTime;

    private String authorization;
    private String placeOfCode;
    private List<CaseDiagnosis> caseDiagnosis;



    private List<ServiceLine> serviceLines;
}
