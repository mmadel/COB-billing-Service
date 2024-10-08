package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.request.InvoiceBillingProviderInformation;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.electronic.enums.BillingProviderTaxIDType;
import org.springframework.stereotype.Component;

@Component
public class BillingProviderFiller {
    public void fill(InvoiceBillingProviderInformation invoiceBillingProviderInformation, Claim claim) {
        claim.setBill_name(invoiceBillingProviderInformation.getBusinessName());
        claim.setBill_addr_1(invoiceBillingProviderInformation.getAddress());
        String[] cityStateZip = invoiceBillingProviderInformation.getCity_state_zip().split(",");
        claim.setBill_city(cityStateZip[0]);
        claim.setBill_state(cityStateZip[1].split(" ")[0]);
        claim.setBill_zip(cityStateZip[1].split(" ")[1]);
        claim.setBill_npi(invoiceBillingProviderInformation.getNpi());
        claim.setBill_phone(invoiceBillingProviderInformation.getPhone());
        claim.setBill_taxid(invoiceBillingProviderInformation.getTaxId());
        claim.setBill_taxid_type(BillingProviderTaxIDType.EIN.getValue());
    }
}
