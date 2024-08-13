package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.tmp.InvoiceInsuranceCompanyInformation;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

@Component
public class PayerFiller {
    public void fill(InvoiceInsuranceCompanyInformation invoiceInsuranceCompanyInformation, Claim claim) {
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
            //payer_order
            invoiceInsuranceCompanyInformation.getInsuranceType();
        }
        fillInsuranceType(invoiceInsuranceCompanyInformation.getInsuranceType(), claim);
    }

    private void fillInsuranceType(String insuranceType, Claim claim) {
        switch (insuranceType) {
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
                break;
            case "Not_Known":
                claim.setOther_claimfilingcode("ZZ");
                break;

        }

    }
}
