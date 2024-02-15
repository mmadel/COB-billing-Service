package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

@Component
public class FacilityFiller {
    public void fill(Clinic clinic, Claim claim){
        claim.setFacility_addr_1(clinic.getClinicdata().getAddress());
        claim.setFacility_city(clinic.getClinicdata().getCity());
        claim.setFacility_state(clinic.getClinicdata().getState());
        claim.setFacility_zip(clinic.getClinicdata().getZipCode());
        claim.setFacility_npi(clinic.getNpi());
        claim.setFacility_zip(clinic.getTitle());
    }
}
