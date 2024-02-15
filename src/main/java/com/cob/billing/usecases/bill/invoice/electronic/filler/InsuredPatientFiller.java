package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.tmp.InvoicePatientInsuredInformation;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class InsuredPatientFiller {
    public void fill(InvoicePatientInsuredInformation invoicePatientInsuredInformation, Claim claim) {
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
        claim.setIns_sex(invoicePatientInsuredInformation.getGender().getValue());
        claim.setPat_rel(getPatientRelation(invoicePatientInsuredInformation.getRelationToInsured()));
    }

    private String getPatientRelation(String PatientRelationValue) {
        if (PatientRelationValue.equals("Self"))
            return "18";
        if (PatientRelationValue.equals("Spouse"))
            return "01";
        if (PatientRelationValue.equals("Other"))
            return "G8";
        return "";
    }
}
