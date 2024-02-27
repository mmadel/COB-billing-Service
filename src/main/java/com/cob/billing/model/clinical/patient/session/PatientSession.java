package com.cob.billing.model.clinical.patient.session;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientSession {
    private Long id;
    private Long  patientId;
    private DoctorInfo doctorInfo;
    private ClinicInfo clinicInfo;
    private Clinic clinic;
    private Long serviceDate;
    private Long serviceStartTime;
    private Long serviceEndTime;
    private String authorization;
    private String placeOfCode;
    private String caseTitle;
    private List<CaseDiagnosis> caseDiagnosis;
    private List<ServiceLine> serviceCodes;
    private Boolean isCasesAttached;
    private Boolean isCorrectSession;
}
