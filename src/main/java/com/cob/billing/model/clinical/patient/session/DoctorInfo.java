package com.cob.billing.model.clinical.patient.session;

import com.cob.billing.model.clinical.provider.LegacyID;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class DoctorInfo {
    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorNPI;
    private LegacyID legacyID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorInfo that = (DoctorInfo) o;
        return doctorNPI.equals(that.doctorNPI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorNPI);
    }
}
