package com.cob.billing.usecases.bill.invoice.cms.creator.models;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MultipleItemsWrapper {
    private DoctorInfo provider;
    private Clinic clinic;
    private String patientCase;
    private Long date;
    private String authorization;

    public MultipleItemsWrapper(DoctorInfo provider){
        this.provider = provider;
    }
    public MultipleItemsWrapper(Clinic clinic){
        this.clinic =clinic;
    }
    public MultipleItemsWrapper(String patientCase){
        this.patientCase = patientCase;
    }
    public MultipleItemsWrapper(Long date){
        this.date = date;
    }
    public MultipleItemsWrapper(String[] authorization){
        this.authorization = authorization[0];
    }
}
