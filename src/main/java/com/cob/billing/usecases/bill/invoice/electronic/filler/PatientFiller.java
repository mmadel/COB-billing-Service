package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.request.InvoicePatientInformation;
import com.cob.billing.model.clinical.patient.advanced.PatientAdvancedInformation;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class PatientFiller {
    public void fill(InvoicePatientInformation patientInformation, Claim claim) {
        claim.setPat_addr_1(patientInformation.getAddress().getFirst());
        claim.setPat_city(patientInformation.getAddress().getCity());
        claim.setPat_state(patientInformation.getAddress().getState());
        claim.setPat_zip(patientInformation.getAddress().getZipCode());
        SimpleDateFormat dobFormat = new SimpleDateFormat("yyyy/MM/dd");
        claim.setPat_dob(dobFormat.format(patientInformation.getDateOfBirth()));
        if (patientInformation.getPatientAdvancedInformation() != null) {
            claim.setAccident_date(dobFormat.format(patientInformation.getPatientAdvancedInformation().getPatientAdvancedDates().getAccident()));
            claim.setAuto_accident(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isAutoAccident() ? "Y" : "N");
            claim.setEmployment_related(patientInformation.getPatientAdvancedInformation().getPateintAdvancedCondtion().isEmployment() ? "Y" : "N");
        }
        claim.setPat_name_f(patientInformation.getFirstName());
        claim.setPat_name_l(patientInformation.getLastName());
        switch (patientInformation.getBox26()) {
            case "primaryId":
                claim.setPcn(patientInformation.getInsuredPrimaryId());
                break;
            case "ssn":
                claim.setPcn(patientInformation.getSsn());
                break;
            case "externalId":
                claim.setPcn(patientInformation.getExternalId());
                break;
        }
        claim.setPat_sex(patientInformation.getGender().toString().charAt(0) + "");
        if (patientInformation.getPatientAdvancedInformation() != null)
            fillAdvancedInformation(patientInformation.getPatientAdvancedInformation(), claim);
        if (patientInformation.getReferringProvider() != null)
            fillReferringProvider(patientInformation.getReferringProvider(), claim);
    }

    private void fillAdvancedInformation(PatientAdvancedInformation patientAdvancedInformation, Claim claim) {
        //Box 19
        if (patientAdvancedInformation.getAdditionalInformation() != null)
            claim.setNarrative(patientAdvancedInformation.getAdditionalInformation());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //Fix Box14
        if (patientAdvancedInformation.getPatientAdvancedDates().getFirstSymptoms() != null)
            claim.setCond_date(simpleDateFormat.format(patientAdvancedInformation.getPatientAdvancedDates().getFirstSymptoms()));
        //Fill Box15
        if (patientAdvancedInformation.getPatientAdvancedDates().getLastSeenByDoctor() != null)
            claim.setLastseen_date(simpleDateFormat.format(patientAdvancedInformation.getPatientAdvancedDates().getLastSeenByDoctor()));
        if (patientAdvancedInformation.getPatientAdvancedDates().getAccident() != null) {
            claim.setOnset_date(simpleDateFormat.format(patientAdvancedInformation.getPatientAdvancedDates().getAccident()));
            claim.setEmployment_related(patientAdvancedInformation.getPateintAdvancedCondtion().isEmployment() ? "Y" : "N");
            claim.setAuto_accident(patientAdvancedInformation.getPateintAdvancedCondtion().isAutoAccident() ? "Y" : "N");
            claim.setAuto_accident_state(patientAdvancedInformation.getPateintAdvancedCondtion().getState());
            claim.setOther_accident(patientAdvancedInformation.getPateintAdvancedCondtion().isOtherAccident() ? "Y" : "N");
        }
        if (patientAdvancedInformation.getPatientAdvancedDates().getFirstTreatment() != null)
            claim.setInitial_treatment_date(simpleDateFormat.format(patientAdvancedInformation.getPatientAdvancedDates().getFirstTreatment()));
        //Fix Box16
        if (patientAdvancedInformation.getUnableToWorkStartDate() != null && patientAdvancedInformation.getUnableToWorkEndDate() != null) {
            claim.setNowork_from_date(simpleDateFormat.format(patientAdvancedInformation.getUnableToWorkStartDate()));
            claim.setNowork_to_date(simpleDateFormat.format(patientAdvancedInformation.getUnableToWorkEndDate()));
        }
        //Fix Box18
        if (patientAdvancedInformation.getHospitalizedStartDate() != null && patientAdvancedInformation.getHospitalizedEndDate() != null) {
            claim.setHosp_from_date(simpleDateFormat.format(patientAdvancedInformation.getHospitalizedStartDate()));
            claim.setHosp_thru_date(simpleDateFormat.format(patientAdvancedInformation.getHospitalizedEndDate()));
        }
    }

    private void fillReferringProvider(ReferringProvider referringProvider, Claim claim) {
        claim.setRef_name_l(referringProvider.getLastName());
        claim.setRef_name_f(referringProvider.getFirstName());
        claim.setRef_npi(referringProvider.getNpi());
        claim.setRef_id(referringProvider.getReferringProviderId());
    }
}
