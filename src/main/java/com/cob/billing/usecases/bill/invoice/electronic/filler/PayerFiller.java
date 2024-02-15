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
}
