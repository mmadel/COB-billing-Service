package com.cob.billing.model.bill.invoice;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SelectedSessionServiceLine {
    private PatientSession sessionId;
    private ServiceLine serviceLine;
}
