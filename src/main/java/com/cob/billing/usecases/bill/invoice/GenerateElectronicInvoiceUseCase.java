package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.tmp.*;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Charge;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.model.integration.claimmd.ClaimUploadRequest;
import com.cob.billing.usecases.bill.invoice.cms.finder.ClinicModelFinder;
import com.cob.billing.usecases.bill.invoice.cms.finder.ProviderModelFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateElectronicInvoiceUseCase {

    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;

    public ClaimUploadRequest generate(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<PatientInvoiceEntity> createdInvoicesRecords = createInvoiceRecordUseCase.createRecord(invoiceRequest);

        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());

        return CreateElectronicClaim(invoiceRequest, createdInvoicesRecords);
    }

    private ClaimUploadRequest CreateElectronicClaim(InvoiceRequest invoiceRequest, List<PatientInvoiceEntity> patientInvoiceRecords) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Claim claim = new Claim();
        fillBillProvider(invoiceRequest.getInvoiceBillingProviderInformation(), claim);
        fillInsured(invoiceRequest.getInvoicePatientInsuredInformation(), claim);
        fillPatient(invoiceRequest.getPatientInformation(), claim);
      //  fillProvider(ProviderModelFinder.find(patientInvoiceRecords), claim);
        fillPayer(invoiceRequest.getInvoiceInsuranceCompanyInformation(), claim);
       // fillFacility(ClinicModelFinder.find(patientInvoiceRecords), claim);
        fillCharge(patientInvoiceRecords, claim);
        ClaimUploadRequest claimUploadRequest = new ClaimUploadRequest();
        List<Claim> claims = new ArrayList<>();
        claims.add(claim);
        claimUploadRequest.setClaim(claims);
        return claimUploadRequest;
    }

    private void fillBillProvider(InvoiceBillingProviderInformation invoiceBillingProviderInformation, Claim claim) {
        claim.setBill_name(invoiceBillingProviderInformation.getBusinessName());
        claim.setBill_addr_1(invoiceBillingProviderInformation.getAddress());
        String[] cityStateZip = invoiceBillingProviderInformation.getCity_state_zip().split(",");
        claim.setBill_city(cityStateZip[0]);
        claim.setBill_state(cityStateZip[1].split(" ")[0]);
        claim.setBill_zip(cityStateZip[1].split(" ")[1]);
        /*
            TODO
            set Billing Provider NPI
         */
        //claim.setBill_npi(invoiceBillingProviderInformation.getNPI());
        claim.setBill_phone(invoiceBillingProviderInformation.getPhone());
        claim.setBill_taxid(invoiceBillingProviderInformation.getTaxId());
    }

    private void fillInsured(InvoicePatientInsuredInformation invoicePatientInsuredInformation, Claim claim) {
        claim.setIns_addr_1(invoicePatientInsuredInformation.getAddress().getFirst());
        claim.setIns_city(invoicePatientInsuredInformation.getAddress().getCity());
        claim.setIns_state(invoicePatientInsuredInformation.getAddress().getState());
        claim.setIns_zip(invoicePatientInsuredInformation.getAddress().getZipCode());
        SimpleDateFormat dobFormat = new SimpleDateFormat("MM/dd/yyyy");
        claim.setIns_dob(dobFormat.format(invoicePatientInsuredInformation.getDateOfBirth()));
        /*
            TODO
            set InvoicePatientInsuredInformation Group
         */
        //claim.setIns_group(invoicePatientInsuredInformation.getGroup());
        claim.setIns_name_f(invoicePatientInsuredInformation.getFirstName());
        claim.setIns_name_l(invoicePatientInsuredInformation.getLastName());
        claim.setIns_number(invoicePatientInsuredInformation.getPhone());
        claim.setIns_sex(invoicePatientInsuredInformation.getGender().toString());
        claim.setPat_rel(invoicePatientInsuredInformation.getRelationToInsured().substring(0, 1));
    }

    private void fillPatient(InvoicePatientInformation patientInformation, Claim claim) {
        claim.setPat_addr_1(patientInformation.getAddress().getFirst());
        claim.setPat_city(patientInformation.getAddress().getCity());
        claim.setPat_state(patientInformation.getAddress().getState());
        claim.setPat_zip(patientInformation.getAddress().getZipCode());
        SimpleDateFormat dobFormat = new SimpleDateFormat("MM/dd/yyyy");
        claim.setPat_dob(dobFormat.format(patientInformation.getDateOfBirth()));
        claim.setPat_name_f(patientInformation.getFirstName());
        claim.setPat_name_l(patientInformation.getLastName());
        claim.setPat_sex(patientInformation.getGender().toString().charAt(0) + "");
        claim.setAuto_accident(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isAutoAccident() ? "Y" : "N");
        //claim.setAccept_assign();
        claim.setEmployment_related(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isEmployment() ? "Y" : "N");
    }

    private void fillProvider(DoctorInfo doctorInfo, Claim claim) {
        claim.setProv_name_f(doctorInfo.getDoctorFirstName());
        claim.setProv_name_l(doctorInfo.getDoctorLastName());
        /*
                TODO
                add middle name to provider
         */
        //claim.setProv_name_m(doctorInfo.getDoctorMiddleName());
        claim.setProv_npi(doctorInfo.getDoctorNPI());
        /*
                TODO
                add Taxonomy to provider
         */
        //claim.setProv_taxonomy(doctorInfo.getTaxonomy());
    }

    private void fillPayer(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation, Claim claim) {
        String assigned[] = invoiceInsuranceCompanyInformation.getAssigner();
        if (assigned != null) {
            claim.setPayer_name(assigned[1]);
            claim.setPayer_addr_1(assigned[2]);
            String[] cityStateZipCode = assigned[3].split(",");
            claim.setPayer_city(cityStateZipCode[0]);
            claim.setPayer_state(cityStateZipCode[1].split(" ")[0]);
            claim.setPayer_zip(cityStateZipCode[1].split(" ")[1]);
            claim.setPayerid(assigned[0]);
            //payer_order
        } else {
            claim.setPayer_name(invoiceInsuranceCompanyInformation.getName());
            claim.setPayer_addr_1(invoiceInsuranceCompanyInformation.getAddress().getAddress() == null ? "" : invoiceInsuranceCompanyInformation.getAddress().getAddress());
            claim.setPayer_city(invoiceInsuranceCompanyInformation.getAddress().getCity());
            claim.setPayer_state(invoiceInsuranceCompanyInformation.getAddress().getState());
            claim.setPayer_zip(invoiceInsuranceCompanyInformation.getAddress().getZipCode());
            /*
                TODO
                get payer id in case of insurance company is external , so assigned is empty
             */
            //claim.setPayerid();
        }
    }

    private void fillFacility(Clinic clinic, Claim claim) {
        claim.setFacility_addr_1(clinic.getClinicdata().getAddress());
        claim.setFacility_city(clinic.getClinicdata().getCity());
        claim.setFacility_state(clinic.getClinicdata().getState());
        claim.setFacility_zip(clinic.getClinicdata().getZipCode());
        claim.setFacility_npi(clinic.getNpi());
        claim.setFacility_zip(clinic.getTitle());
    }

    private void fillCharge(List<PatientInvoiceEntity> patientInvoiceRecords, Claim claim) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Charge> charges = new ArrayList<>();
        for (PatientInvoiceEntity patientInvoice : patientInvoiceRecords) {
            Charge charge = new Charge();
            charge.setCharge(String.valueOf(patientInvoice.getServiceLine().getCptCode().getCharge()));
            charge.setCharge_record_type("UN");
            SimpleDateFormat serviceDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            charge.setFrom_date(serviceDateFormat.format(patientInvoice.getPatientSession().getServiceDate()));
            charge.setThru_date(serviceDateFormat.format(patientInvoice.getPatientSession().getServiceDate()));
            charge.setPlace_of_service(patientInvoice.getPatientSession().getPlaceOfCode().split("_")[1]);
            charge.setProc_code(patientInvoice.getServiceLine().getCptCode().getServiceCode());
            /*TODO
                generate id with max 40 length
             */
            //charge.setRemote_chgid();
            if (patientInvoice.getServiceLine().getCptCode().getModifier().length() > 0) {
                String[] modList = patientInvoice.getServiceLine().getCptCode().getModifier().split("\\.");
                for (int i = 0; i < modList.length; i++) {
                    if (modList[i] != null) {
                        Method method = Charge.class.getMethod("setMod" + (i + 1), String.class);
                        method.invoke(charge, modList[i]);
                    }
                }
            }
            List<PatientSessionEntity> sessions = new ArrayList<>();
            if (!containsSession(sessions, patientInvoice.getPatientSession().getId()))
                sessions.add(patientInvoice.getPatientSession());
            List<String> sessionDiagnosis = new ArrayList<>();
            for (PatientSessionEntity patientSession : sessions) {
                patientSession.getCaseDiagnosis().stream()
                        .forEach(caseDiagnosis -> sessionDiagnosis.add(caseDiagnosis.getDiagnosisCode()));
            }
            List<String> indexes = new ArrayList<>();
            if (patientInvoice.getServiceLine().getDiagnoses() != null) {
                for (String serviceLineDiagnosis : patientInvoice.getServiceLine().getDiagnoses()) {
                    int index = sessionDiagnosis.indexOf(serviceLineDiagnosis);
                    if (index != -1)
                        indexes.add(getCharForNumber(index + 1));
                }
            }
            charge.setDiag_ref(String.join("", indexes));
            charge.setUnits(patientInvoice.getServiceLine().getCptCode().getUnit().toString());
            charges.add(charge);
        }
        getSessionDiagnosis(patientInvoiceRecords, claim);
        claim.setCharge(charges);
    }

    private void getSessionDiagnosis(List<PatientInvoiceEntity> patientInvoices, Claim claim) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<PatientSessionEntity> sessions = new ArrayList<>();
        for (PatientInvoiceEntity patientInvoice : patientInvoices) {
            if (!containsSession(sessions, patientInvoice.getPatientSession().getId()))
                sessions.add(patientInvoice.getPatientSession());
        }
        List<String> sessionDiagnosis = new ArrayList<>();
        for (PatientSessionEntity patientSession : sessions) {
            patientSession.getCaseDiagnosis().stream()
                    .forEach(caseDiagnosis -> sessionDiagnosis.add(caseDiagnosis.getDiagnosisCode()));
        }
        fillDiagnosis(sessionDiagnosis, claim);
    }

    private void fillDiagnosis(List<String> sessionDiagnosis, Claim claim) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int counter = 1;
        for (String diago : sessionDiagnosis) {
            Method method = Claim.class.getMethod("setDiag_" + counter, String.class);
            method.invoke(claim, diago);
            counter++;
        }
    }

    private boolean containsSession(List<PatientSessionEntity> list, Long id) {
        return list.stream().anyMatch(p -> p.getId().equals(id));
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }
}
