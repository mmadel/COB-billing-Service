package com.cob.billing.usecases.bill.invoice.electronic.creator;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.invoice.response.tmp.InvoiceResponse;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.FindClinicAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.FindProviderAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.filler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateElectronicFieldsUseCase {
    @Autowired
    private BillingProviderFiller billingProviderFiller;
    @Autowired
    private InsuredPatientFiller insuredPatientFiller;
    @Autowired
    private PatientFiller patientFiller;
    @Autowired
    private ProviderFiller providerFiller;
    @Autowired
    private PayerFiller payerFiller;
    @Autowired
    private ServiceLinesFiller serviceLinesFiller;
    @Autowired
    FacilityFiller facilityFiller;
    @Autowired
    CorrectClaimFiller correctClaimFiller;

    public List<Claim> create(InvoiceRequest invoiceRequest, InvoiceResponse invoiceResponse) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        for (Map.Entry<PatientSession, List<SelectedSessionServiceLine>> entry : invoiceResponse.getSessions().entrySet()) {
            PatientSession patientSession = entry.getKey();
            List<SelectedSessionServiceLine> serviceLines = entry.getValue();
            Claim claim = new Claim();
            billingProviderFiller.fill(invoiceRequest.getInvoiceBillingProviderInformation(), claim);
            insuredPatientFiller.fill(invoiceRequest.getInvoicePatientInsuredInformation(), claim);
            patientFiller.fill(invoiceRequest.getPatientInformation(), claim);
            providerFiller.fill(patientSession.getDoctorInfo(), claim);
            facilityFiller.fill(patientSession.getClinic(), claim);
            payerFiller.fill(invoiceRequest.getInvoiceInsuranceCompanyInformation(), claim);
            serviceLinesFiller.fill(serviceLines, claim);
            claim.setRemote_claimid(patientSession.getId().toString());
            if (invoiceRequest.getCorrectClaimInformation() != null)
                correctClaimFiller.fill(invoiceRequest.getCorrectClaimInformation(), claim);
            claims.add(claim);
        }
        return claims;
    }
}
