package com.cob.billing.usecases.bill.invoice.electronic.creator;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ElectronicMultipleClaimCreator {
    @Autowired
    ElectronicClaimCreator claimCreator;

    public List<Claim> create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        claimCreator.setInvoiceRequest(invoiceRequest);
        createMultipleProviders(invoiceRequest, claims);
        createMultipleClinics(invoiceRequest, claims);
        createMultipleCases(invoiceRequest, claims);
        createMultipleDates(invoiceRequest, claims);
        return claims;
    }

    private void createMultipleProviders(InvoiceRequest invoiceRequest, List<Claim> claims) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Map<DoctorInfo, List<SelectedSessionServiceLine>>
                providers =
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(serviceLine -> serviceLine.getSessionId().getDoctorInfo()));
        if (providers.size() > 1) {
            for (Map.Entry<DoctorInfo, List<SelectedSessionServiceLine>> entry : providers.entrySet()) {
                claims.add(claimCreator.create(entry.getKey(), entry.getValue()));
            }
        }
    }

    private void createMultipleClinics(InvoiceRequest invoiceRequest, List<Claim> claims) {
        Map<Clinic, List<SelectedSessionServiceLine>> clinics =
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getClinic()));
    }

    private void createMultipleCases(InvoiceRequest invoiceRequest, List<Claim> claims) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Map<String, List<SelectedSessionServiceLine>> cases =
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getCaseTitle()));
        if (cases.size() > 1) {
            for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : cases.entrySet()) {
                claims.add(claimCreator.create(entry.getValue()));
            }
        }
    }

    private void createMultipleDates(InvoiceRequest invoiceRequest, List<Claim> claims) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Map<Long, List<SelectedSessionServiceLine>> dates = invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getServiceDate()));
        if (dates.size() > 1 &&
                isDatePerClaim(invoiceRequest.getInvoiceRequestConfiguration())) {
            for (Map.Entry<Long, List<SelectedSessionServiceLine>> entry : dates.entrySet()) {
                claims.add(claimCreator.create(entry.getValue()));
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
