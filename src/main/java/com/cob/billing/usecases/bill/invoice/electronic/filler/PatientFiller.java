package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.tmp.InvoicePatientInformation;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PatientFiller {
    public void fill(InvoicePatientInformation patientInformation, Claim claim) {
        claim.setPat_addr_1(patientInformation.getAddress().getFirst());
        claim.setPat_city(patientInformation.getAddress().getCity());
        claim.setPat_state(patientInformation.getAddress().getState());
        claim.setPat_zip(patientInformation.getAddress().getZipCode());
        SimpleDateFormat dobFormat = new SimpleDateFormat("yyyy/MM/dd");
        claim.setPat_dob(dobFormat.format(patientInformation.getDateOfBirth()));
        if (patientInformation.getPatientAdvancedInformation()!= null){
            claim.setAccident_date(dobFormat.format(patientInformation.getPatientAdvancedInformation().getPatientAdvancedDates().getAccident()));
            claim.setAuto_accident(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isAutoAccident() ? "Y" : "N");
            claim.setEmployment_related(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isEmployment() ? "Y" : "N");
        }
        claim.setPat_name_f(patientInformation.getFirstName());
        claim.setPat_name_l(patientInformation.getLastName());
        claim.setPcn(patientInformation.getSsn());
        claim.setPat_sex(patientInformation.getGender().toString().charAt(0) + "");
    }
}
