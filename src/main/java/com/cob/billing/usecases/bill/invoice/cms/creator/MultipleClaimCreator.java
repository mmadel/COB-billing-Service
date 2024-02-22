package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultipleClaimCreator {

    @Autowired
    ClaimCreator claimCreator;

    public List<String> create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        List<String> fileNames = new ArrayList<>();
        claimCreator.setInvoiceRequest(invoiceRequest);
        createMultipleProviders(invoiceRequest, fileNames);
        createMultipleClinics(invoiceRequest, fileNames);
        createMultipleCases(invoiceRequest, fileNames);
        createMultipleDates(invoiceRequest, fileNames);
        return fileNames;
    }


    private void createMultipleProviders(InvoiceRequest invoiceRequest, List<String> fileNames) throws IOException, IllegalAccessException {
        Map<DoctorInfo, List<SelectedSessionServiceLine>>
                providers =
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(serviceLine -> serviceLine.getSessionId().getDoctorInfo()));
        if (providers.size() > 1) {
            for (Map.Entry<DoctorInfo, List<SelectedSessionServiceLine>> entry : providers.entrySet()) {
                List<String> files = claimCreator.create(entry.getKey(), entry.getValue());
                fileNames.addAll(files);
            }
        }
    }

    private void createMultipleClinics(InvoiceRequest invoiceRequest, List<String> fileNames) throws IOException, IllegalAccessException {
        Map<Clinic, List<SelectedSessionServiceLine>> clinics =
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getClinic()));
        if (clinics.size() > 1) {
            for (Map.Entry<Clinic, List<SelectedSessionServiceLine>> entry : clinics.entrySet()) {
                List<String> files = claimCreator.create(entry.getKey(), entry.getValue());
                fileNames.addAll(files);
            }
        }
    }

    private void createMultipleCases(InvoiceRequest invoiceRequest, List<String> fileNames) throws IOException, IllegalAccessException {
        Map<String, List<SelectedSessionServiceLine>> cases =
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getCaseTitle()));
        if (cases.size() > 1) {
            for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : cases.entrySet()) {
                List<String> files = claimCreator.create(entry.getKey(), entry.getValue());
                fileNames.addAll(files);
            }
        }
    }

    private void createMultipleDates(InvoiceRequest invoiceRequest, List<String> fileNames) throws IOException, IllegalAccessException {
        Map<Long, List<SelectedSessionServiceLine>> dates = invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getServiceDate()));
        if (dates.size() > 1 &&
                isDatePerClaim(invoiceRequest.getInvoiceRequestConfiguration())) {
            for (Map.Entry<Long, List<SelectedSessionServiceLine>> entry : dates.entrySet()) {
                List<String> files = claimCreator.create(entry.getKey(), entry.getValue());
                fileNames.addAll(files);
            }
        }
    }

    private boolean isDatePerClaim(InvoiceRequestConfiguration invoiceRequestConfiguration) {
        if (invoiceRequestConfiguration.getIsOneDateServicePerClaim() != null) {
            return invoiceRequestConfiguration.getIsOneDateServicePerClaim();
        } else {
            return false;
        }
    }
}
