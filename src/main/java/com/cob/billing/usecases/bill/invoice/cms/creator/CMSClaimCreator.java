package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.PatientSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CMSClaimCreator {
    List<String> create(InvoiceRequest invoiceRequest , Boolean[] flags, Map<PatientSession, List<SelectedSessionServiceLine>> testMap) throws IOException, IllegalAccessException;
}
