package com.cob.billing.usecases.bill.invoice.cms.finder;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.admin.clinic.Clinic;

import java.util.List;

public class ClinicModelFinder {
    public static Clinic find(List<PatientInvoiceEntity> patientInvoiceRecords) {
        Clinic clinic = new Clinic();
        ClinicEntity entity = patientInvoiceRecords.stream()
                .findFirst()
                .get()
                .getPatientSession().getClinic();
        clinic.setId(entity.getId());
        clinic.setNpi(entity.getNpi());
        clinic.setTitle(entity.getTitle());
        clinic.setClinicdata(entity.getClinicdata());
        return clinic;
    }
}
