package com.cob.billing.model.clinical.patient.session;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorInfo {
    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorNPI;
}
