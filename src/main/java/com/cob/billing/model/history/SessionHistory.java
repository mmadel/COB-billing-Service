package com.cob.billing.model.history;

import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.clinical.patient.Patient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SessionHistory {
    private Long submissionId;
    private String insuranceCompany;
    private Long submitDate;
    private Patient client;
    private String provider;
    private SubmissionStatus submissionStatus;
    private SubmissionType submissionType;
    private List<SessionHistoryCount> sessionCounts;
}
