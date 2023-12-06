package com.cob.billing.model.admin.clinic;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Clinic {
    private Long id ;
    private String title;
    private String npi;
    private ClinicData clinicdata;
}
