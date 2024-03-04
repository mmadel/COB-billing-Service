package com.cob.billing.model.clinical.patient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MinimalPatient {
    private Long id;
    private String name;
    private Long dateOfBirth;
    private String email;
}
