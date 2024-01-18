package com.cob.billing.model.bill.posting;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClientPostingPayments {
    private Long clientId;
    private List<PaymentServiceLine> paymentServiceLines;
}
