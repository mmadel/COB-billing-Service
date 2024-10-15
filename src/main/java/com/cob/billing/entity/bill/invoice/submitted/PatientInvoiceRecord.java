package com.cob.billing.entity.bill.invoice.submitted;

import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.clinical.patient.Patient;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "patient_invoice_record")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class PatientInvoiceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "insurance_company_name")
    private String insuranceCompanyName;

    @Column(name = "insurance_company_id")
    private Long insuranceCompanyId;

    @Column(name = "document", length = 100000)
    private byte[] cmsDocument;

    @Column(name = "submission_id")
    private Long submissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_type")
    private SubmissionType submissionType;

    @OneToMany(mappedBy = "patientInvoiceRecord")
    Set<PatientSubmittedClaim> claims;

    @Column(name = "patient", columnDefinition = "jsonb")
    @Type(type = "jsonb")
        private Patient patient;
    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }

}
