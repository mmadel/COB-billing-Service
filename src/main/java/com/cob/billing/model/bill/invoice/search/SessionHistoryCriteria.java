package com.cob.billing.model.bill.invoice.search;

import com.cob.billing.enums.SubmissionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SessionHistoryCriteria {
    private String insuranceCompany;
    private String client;
    private String provider;
    private String claimId;
    private Long dosStart;
    private Long dosEnd;
    private Long submitStart;
    private Long submitEnd;
    private List<SubmissionStatus> selectedStatus;
}
