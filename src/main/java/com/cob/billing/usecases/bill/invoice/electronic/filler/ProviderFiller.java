package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

@Component
public class ProviderFiller {
    public void fill(DoctorInfo doctorInfo, Claim claim) {
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
}
