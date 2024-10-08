package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;

import java.util.List;

public class FindClinicAssignedToServiceLinesUseCase {
    public static Clinic find(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        return selectedSessionServiceLines.stream()
                .findFirst()
                .get()
                .getSessionId().getClinic();
    }
}
