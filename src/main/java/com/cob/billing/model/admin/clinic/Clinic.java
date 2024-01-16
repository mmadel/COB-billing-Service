package com.cob.billing.model.admin.clinic;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Clinic {
    private Long id ;
    private String title;
    private String npi;
    private ClinicData clinicdata;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clinic clinic = (Clinic) o;
        return Objects.equals(id, clinic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
