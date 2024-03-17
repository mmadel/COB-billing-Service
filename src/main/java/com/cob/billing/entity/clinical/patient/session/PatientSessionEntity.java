package com.cob.billing.entity.clinical.patient.session;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyExternalEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import com.cob.billing.model.clinical.patient.session.ClinicInfo;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patient_session")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class PatientSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @Column(name = "doctor_info", columnDefinition = "json")
    @Type(type = "json")
    private DoctorInfo doctorInfo;

    @Column(name = "clinic_info", columnDefinition = "json")
    @Type(type = "json")
    private ClinicInfo clinicInfo;
    @OneToOne
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    ClinicEntity clinic;
    @Column(name = "service_date")
    private Long serviceDate;
    @Column(name = "service_start_time")
    private Long serviceStartTime;
    @Column(name = "service_End_time")
    private Long serviceEndTime;
    @Column(name = "place_of_code")
    private String placeOfCode;
    @Column(name = "case_diagnosis", columnDefinition = "json")
    @Type(type = "json")
    private List<CaseDiagnosis> caseDiagnosis;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "session_id")
    private List<PatientSessionServiceLineEntity> serviceCodes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PatientSessionStatus status;
    @Column(name = "case_title")
    private String caseTitle;
    @ManyToOne
    @JoinColumn(name = "authorization_id", referencedColumnName = "id")
    private PatientAuthorizationEntity patientAuthorization;

    public void addServiceCode(PatientSessionServiceLineEntity entity) {
        this.serviceCodes.add(entity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientSessionEntity that = (PatientSessionEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
