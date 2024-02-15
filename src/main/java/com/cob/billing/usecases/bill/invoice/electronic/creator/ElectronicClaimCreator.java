package com.cob.billing.usecases.bill.invoice.electronic.creator;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.cms.finder.ClinicModelFinder;
import com.cob.billing.usecases.bill.invoice.cms.finder.ProviderModelFinder;
import com.cob.billing.usecases.bill.invoice.electronic.filler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class ElectronicClaimCreator {
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

    private InvoiceRequest invoiceRequest;

    public Claim create() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return createBasicClaimPart(invoiceRequest.getSelectedSessionServiceLine());
    }

    public Claim create(DoctorInfo provider, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Claim claim = new Claim();
        billingProviderFiller.fill(invoiceRequest.getInvoiceBillingProviderInformation(), claim);

        insuredPatientFiller.fill(invoiceRequest.getInvoicePatientInsuredInformation(), claim);
        patientFiller.fill(invoiceRequest.getPatientInformation(), claim);
        providerFiller.fill(provider, claim);
        payerFiller.fill(invoiceRequest.getInvoiceInsuranceCompanyInformation(), claim);
        serviceLinesFiller.fill(selectedSessionServiceLine, claim);
        return claim;

    }

    public Claim create(Clinic clinic, List<SelectedSessionServiceLine> selectedSessionServiceLine) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Claim claim = new Claim();
        billingProviderFiller.fill(invoiceRequest.getInvoiceBillingProviderInformation(), claim);
        insuredPatientFiller.fill(invoiceRequest.getInvoicePatientInsuredInformation(), claim);
        patientFiller.fill(invoiceRequest.getPatientInformation(), claim);
        providerFiller.fill(ProviderModelFinder.find(invoiceRequest.getSelectedSessionServiceLine()), claim);
        facilityFiller.fill(clinic, claim);
        payerFiller.fill(invoiceRequest.getInvoiceInsuranceCompanyInformation(), claim);
        serviceLinesFiller.fill(selectedSessionServiceLine, claim);
        return claim;
    }

    public Claim create(List<SelectedSessionServiceLine> selectedSessionServiceLine) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return createBasicClaimPart(selectedSessionServiceLine);
    }

    public void setInvoiceRequest(InvoiceRequest invoiceRequest) {
        this.invoiceRequest = invoiceRequest;
    }

    private Claim createBasicClaimPart(List<SelectedSessionServiceLine> selectedSessionServiceLine) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Claim claim = new Claim();
        billingProviderFiller.fill(invoiceRequest.getInvoiceBillingProviderInformation(), claim);
        insuredPatientFiller.fill(invoiceRequest.getInvoicePatientInsuredInformation(), claim);
        patientFiller.fill(invoiceRequest.getPatientInformation(), claim);
        providerFiller.fill(ProviderModelFinder.find(invoiceRequest.getSelectedSessionServiceLine()), claim);
        facilityFiller.fill(ClinicModelFinder.find(invoiceRequest.getSelectedSessionServiceLine()), claim);
        payerFiller.fill(invoiceRequest.getInvoiceInsuranceCompanyInformation(), claim);
        serviceLinesFiller.fill(selectedSessionServiceLine, claim);
        return claim;
    }

}
