package com.cob.billing.usecases.bill.invoice.cms.finder;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;

import java.util.List;

public class ClinicModelFinder {
    public static Clinic find(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        return selectedSessionServiceLines.stream()
                .findFirst()
                .get()
                .getSessionId().getClinic();
    }
}
