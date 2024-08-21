package com.cob.billing.entity.clinical.patient.session;

import com.cob.billing.enums.ClaimResponseStatus;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleLineModel;
import com.cob.billing.model.integration.claimmd.MessageResponse;
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
@Table(name = "patient_session_claim_response")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Getter
@Setter
public class PatientSessionClaimResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    PatientSessionEntity patientSession;

    @Column(name = "claim_md_id")
    private Long claimId;

    @Column(name = "submitted_file_name")
    private String submittedFileName;

    @Column(name = "patient_id")
    private String patientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "calim_status")
    private ClaimResponseStatus claimResponseStatus;

    @Column(name = "claim_message_response", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<MessageResponse> messages;

}
