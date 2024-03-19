package com.cob.billing.model.bill.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class SubmissionSession {
    private Long sessionId;
    private Long dateOfService;
    private Long insuranceCompanyId;
    private AuthorizationSession authorizationSession;
    private List<AuthorizationSession> patientAuthorizations;
}
