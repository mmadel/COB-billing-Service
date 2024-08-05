package com.cob.billing.entity.clinical.patient;

import com.cob.billing.model.clinical.patient.CaseDiagnosis;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient_case")
@Getter
@Setter
public class PatientCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "case_diagnosis", columnDefinition = "json")
    @Type(type = "jsonb")
    private List<CaseDiagnosis> caseDiagnosis;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientEntity patient;

}
