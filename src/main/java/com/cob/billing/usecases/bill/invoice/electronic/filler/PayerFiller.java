package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.tmp.InvoiceInsuranceCompanyInformation;
import com.cob.billing.model.bill.invoice.tmp.OtherPatientInsurance;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.cms.rules.OtherInsuranceSelectionRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayerFiller {
    @Autowired
    private OtherInsuranceSelectionRules otherInsuranceSelectionRules;

    public void fill(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation, Claim claim) throws IllegalAccessException {
        String assigned[] = invoiceInsuranceCompanyInformation.getAssigner();

        if (assigned != null) {
            claim.setPayer_name(assigned[1]);
            claim.setPayer_addr_1(assigned[2]);
            String[] cityStateZipCode = assigned[3].split(",");
            claim.setPayer_city(cityStateZipCode[0]);
            claim.setPayer_state(cityStateZipCode[1].split(" ")[0]);
            claim.setPayer_zip(cityStateZipCode[1].split(" ")[1]);
            claim.setPayerid(assigned[0]);
            if (invoiceInsuranceCompanyInformation.getIsAssignment()) {
                claim.setAccept_assign("Y");
            } else {
                claim.setAccept_assign("N");
            }
        }else {
            claim.setPayer_name(invoiceInsuranceCompanyInformation.getName());
            claim.setPayer_addr_1(invoiceInsuranceCompanyInformation.getAddress().getAddress() == null ? "" : invoiceInsuranceCompanyInformation.getAddress().getAddress());
            claim.setPayer_city(invoiceInsuranceCompanyInformation.getAddress().getCity());
            claim.setPayer_state(invoiceInsuranceCompanyInformation.getAddress().getState());
            claim.setPayer_zip(invoiceInsuranceCompanyInformation.getAddress().getZipCode());
        }
        fillInsuranceType(invoiceInsuranceCompanyInformation, claim);
        fillOtherInsurance(invoiceInsuranceCompanyInformation ,claim);
        fillInsuredData(invoiceInsuranceCompanyInformation ,claim);
    }

    private void fillInsuranceType(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation, Claim claim) {
        switch (invoiceInsuranceCompanyInformation.getInsuranceType()) {
            case "Commercial_Insurance":
                claim.setOther_claimfilingcode("CI");
                break;
            case "Preferred_Provider_Organization":
                claim.setOther_claimfilingcode("12");
                break;
            case "Exclusive_Provider_Organization":
                claim.setOther_claimfilingcode("14");
                break;
            case "Health_Maintenance_Organization":
                claim.setOther_claimfilingcode("16");
                break;
            case "Automobile_Medical":
                claim.setOther_claimfilingcode("AM");
                claim.setIns_employer(invoiceInsuranceCompanyInformation.getPolicyInformation()[4]);
                break;
            case "Champus":
                claim.setOther_claimfilingcode("CH");
                break;
            case "Federal_Employees_Program":
                claim.setOther_claimfilingcode("FI");
                break;
            case "Medicare_Part_A":
                claim.setOther_claimfilingcode("MA");
                break;
            case "Medicare_Part_B":
                claim.setOther_claimfilingcode("MB");
                break;
            case "Medicaid":
                claim.setOther_claimfilingcode("MC");
                break;
            case "Workers_Compensation_Health_Claim":
                claim.setOther_claimfilingcode("WC");
                claim.setIns_employer(invoiceInsuranceCompanyInformation.getPolicyInformation()[4]);
                break;
            case "Not_Known":
                claim.setOther_claimfilingcode("ZZ");
                break;

        }

    }


    private void fillOtherInsurance(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation , Claim claim) throws IllegalAccessException {
        OtherPatientInsurance otherPatientInsuranceValues =otherInsuranceSelectionRules.select(invoiceInsuranceCompanyInformation.getOtherInsurances(), invoiceInsuranceCompanyInformation.getPolicyInformation()[2]);
        if(otherPatientInsuranceValues !=null && otherPatientInsuranceValues.getAssigner() !=null){
            String[] insuredName = otherPatientInsuranceValues.getInsuredName().split(",");
            claim.setOther_ins_name_l(insuredName[0]);
            claim.setOther_ins_name_f(insuredName[1]);
            claim.setOther_ins_number(otherPatientInsuranceValues.getPolicyGroup());
            claim.setOther_payer_name(otherPatientInsuranceValues.getAssigner()[1]);
            claim.setOther_payerid(otherPatientInsuranceValues.getAssigner()[0]);
        }
    }
    private void fillInsuredData(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation , Claim claim){
        claim.setIns_plan(invoiceInsuranceCompanyInformation.getPolicyInformation()[1]);
        claim.setIns_group(invoiceInsuranceCompanyInformation.getPolicyInformation()[0]);

    }
}
