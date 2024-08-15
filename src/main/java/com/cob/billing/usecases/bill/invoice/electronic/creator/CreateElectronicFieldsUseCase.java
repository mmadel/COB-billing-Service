package com.cob.billing.usecases.bill.invoice.electronic.creator;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.FindClinicAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.FindProviderAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.filler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

    public Claim create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return create(invoiceRequest,null,null,null);
    }

    public Claim create(InvoiceRequest invoiceRequest, DoctorInfo provider, Clinic clinic, List<SelectedSessionServiceLine> serviceLines) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Claim claim = new Claim();
        billingProviderFiller.fill(invoiceRequest.getInvoiceBillingProviderInformation(), claim);
        insuredPatientFiller.fill(invoiceRequest.getInvoicePatientInsuredInformation(), claim);
        patientFiller.fill(invoiceRequest.getPatientInformation(), claim);
        providerFiller.fill(provider == null ? FindProviderAssignedToServiceLinesUseCase.find(invoiceRequest.getSelectedSessionServiceLine()) : provider, claim);
        facilityFiller.fill(clinic == null ? FindClinicAssignedToServiceLinesUseCase.find(invoiceRequest.getSelectedSessionServiceLine()) : clinic, claim);
        payerFiller.fill(invoiceRequest.getInvoiceInsuranceCompanyInformation(), claim);
        //For Multiple Dates of Patient Cases
        serviceLinesFiller.fill(serviceLines == null ? invoiceRequest.getSelectedSessionServiceLine() : serviceLines, claim);
        correctClaimFiller.fill(invoiceRequest.getCorrectClaimInformation(),claim);
        return claim;
    }
}
