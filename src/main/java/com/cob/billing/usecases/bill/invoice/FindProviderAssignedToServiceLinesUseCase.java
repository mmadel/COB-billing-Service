package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;

import java.util.List;

public class FindProviderAssignedToServiceLinesUseCase {
    public static DoctorInfo find(List<SelectedSessionServiceLine> patientInvoiceRecords) {
        return patientInvoiceRecords.stream()
                .findFirst()
                .get()
                .getSessionId().getDoctorInfo();
    }
}
