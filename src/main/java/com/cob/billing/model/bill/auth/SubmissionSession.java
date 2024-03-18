package com.cob.billing.model.bill.auth;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SubmissionSession {
    private PatientSession patientSession;
    private Long insuranceCompanyId;
    private Long patientId;
}
