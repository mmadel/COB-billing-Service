package com.cob.billing.entity.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.enums.Gender;
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
    @JoinColumn(name="patient_id")
    private PatientEntity patient;
    @Column(name = "doctor_info", columnDefinition = "json")
    @Type(type = "json")
    private DoctorInfo doctorInfo;

    @Column(name = "clinic_info", columnDefinition = "json")
    @Type(type = "json")
    private ClinicInfo clinicInfo;
    @Column(name = "service_date")
    private Long serviceDate;
    @Column(name = "service_start_time")
    private Long serviceStartTime;
    @Column(name = "service_End_time")
    private Long serviceEndTime;
    @Column(name = "authorization")
    private String authorization;
    @Column(name = "place_of_code")
    private String placeOfCode;
    @Column(name = "case_diagnosis", columnDefinition = "json")
    @Type(type = "json")
    private CaseDiagnosis caseDiagnosis;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "session_id")
    private List<ServiceLineEntity> serviceCodes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PatientSessionStatus status;


    public void addServiceCode(ServiceLineEntity entity){
        this.serviceCodes.add(entity);
    }
}
