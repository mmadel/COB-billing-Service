package com.cob.billing.entity.bill.era;

import com.cob.billing.model.bill.posting.era.ERADetailsLine;
import com.cob.billing.model.bill.posting.era.ERALineTransferModel;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "era_history")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class ERAHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ear_id")
    private Integer eraId;

    @Column(name = "era_line", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<ERALineTransferModel> historyLines;

    @Column(name = "is_archive")
    private boolean isArchive;

    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }
}
