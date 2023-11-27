package com.cob.billing.entity.clinical.patient.session;

import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.clinical.patient.session.PatientInfo;
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
    @Column(name = "patient_info", columnDefinition = "json")
    @Type(type = "json")
    private PatientInfo patientInfo;
    @Column(name = "doctor_info", columnDefinition = "json")
    @Type(type = "json")
    private DoctorInfo doctorInfo;
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
    private List<CaseDiagnosis> caseDiagnosis;

    @OneToMany(mappedBy="patientSession")
    private List<ServiceLineEntity> serviceLines;
}
