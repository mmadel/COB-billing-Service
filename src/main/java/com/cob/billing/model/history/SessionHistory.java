package com.cob.billing.model.history;

import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SessionHistory {
    private Long submissionId;
    private String insuranceCompany;
    private Long submitDate;
    private String client;
    private String provider;
    private Long dateOfService;
    private Integer numberOfServiceLines;
    private SubmissionStatus submissionStatus;
    private SubmissionType submissionType;
}
