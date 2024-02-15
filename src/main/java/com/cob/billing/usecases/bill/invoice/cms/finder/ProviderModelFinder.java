package com.cob.billing.usecases.bill.invoice.cms.finder;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;

import java.util.List;

public class ProviderModelFinder {
    public static DoctorInfo find(List<SelectedSessionServiceLine> patientInvoiceRecords) {
        return patientInvoiceRecords.stream()
                .findFirst()
                .get()
                .getSessionId().getDoctorInfo();
    }
}
