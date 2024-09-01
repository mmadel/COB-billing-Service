package com.cob.billing.model.clinical.patient.session;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class PatientSession {
    private Long id;
    private Long patientId;
    private String patientName;
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
    private String authorizationNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientSession that = (PatientSession) o;
        return id.equals(that.id) && doctorInfo.equals(that.doctorInfo) && serviceDate.equals(that.serviceDate) && caseTitle.equals(that.caseTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctorInfo, serviceDate, caseTitle);
    }
}
