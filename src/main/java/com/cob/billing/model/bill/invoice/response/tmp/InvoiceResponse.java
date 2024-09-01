package com.cob.billing.model.bill.invoice.response.tmp;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.integration.claimmd.submit.SubmitResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class InvoiceResponse {
    Map<PatientSession, List<SelectedSessionServiceLine>> sessions;
    List<String> claimsFileNames;
    SubmitResponse clearingHouseClaimsResponse;
}
