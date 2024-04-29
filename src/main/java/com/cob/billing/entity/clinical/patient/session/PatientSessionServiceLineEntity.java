package com.cob.billing.entity.clinical.patient.session;

import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.model.clinical.patient.CaseDiagnosis;
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
@Table(name = "patient_session_service_line")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class PatientSessionServiceLineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cpt_code", columnDefinition = "json")
    @Type(type = "json")
    private CPTCode cptCode;
    @Column(name = "type")
    private String type;
    @Column(name = "diagnoses", columnDefinition = "json")
    @Type(type = "json")
    private List<String> diagnoses;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "line_note")
    private String lineNote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientSessionServiceLineEntity that = (PatientSessionServiceLineEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
