package com.cob.billing.model.bill.invoice.tmp;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InvoiceResponse {
    private Long patientId;
    private String patientName;
    private List<PatientSession> patientSession;
}
