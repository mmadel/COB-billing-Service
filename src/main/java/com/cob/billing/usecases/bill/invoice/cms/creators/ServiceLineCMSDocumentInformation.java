package com.cob.billing.usecases.bill.invoice.cms.creators;


import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.usecases.bill.invoice.cms.models.ServiceLineModel;
import com.cob.billing.util.DateConstructor;

import java.util.ArrayList;
import java.util.List;

public class ServiceLineCMSDocumentInformation extends CMSDocument {
    List<ServiceLineModel> serviceLines;
    List<PatientInvoiceEntity> patientInvoices;

    @Override
    public void create() {
        serviceLines = new ArrayList<>();
        patientInvoices.stream()
                .forEach(patientInvoice -> {
                    String[] dateOfService = DateConstructor.construct(patientInvoice.getPatientSession().getServiceDate());
                    serviceLines.add(ServiceLineModel.builder()
                            .sv_mm_from(dateOfService[0])
                            .sv_dd_from(dateOfService[1])
                            .sv_yy_from(dateOfService[2])

                            .sv_mm_end(dateOfService[0])
                            .sv_dd_end(dateOfService[1])
                            .sv_yy_end(dateOfService[2])
                            .cpt(patientInvoice.getServiceLine().getCptCode().getServiceCode())
                            .ch(patientInvoice.getServiceLine().getCptCode().getCharge().toString())
                            .day(patientInvoice.getServiceLine().getCptCode().getUnit().toString())
                            .place(patientInvoice.getPatientSession().getPlaceOfCode())
                            .local(patientInvoice.getPatientSession().getDoctorInfo().getDoctorNPI())
                            .mod(patientInvoice.getServiceLine().getCptCode().getModifier().split("\\."))
                            .build());
                });
    }

    public void setPatientInvoices(List<PatientInvoiceEntity> patientInvoices) {
        this.patientInvoices = patientInvoices;
    }

    public List<ServiceLineModel> getModel() {
        return serviceLines;
    }
}
