package com.cob.billing.entity.bill.invoice.submitted;

import com.cob.billing.model.clinical.patient.CPTCode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient_submitted_claim_service_line")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class PatientSubmittedClaimServiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "service_line_id")
    private Long serviceLineId;
    @Column(name = "cpt_code", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private CPTCode cptCode;
    @Column(name = "type")
    private String type;
    @Column(name = "diagnoses", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<String> diagnoses;
    @Column(name = "is_correct")
    private Boolean isCorrect;
    @Column(name = "line_note")
    private String lineNote;
    @Column(name = "payments")
    private double payments;
}
