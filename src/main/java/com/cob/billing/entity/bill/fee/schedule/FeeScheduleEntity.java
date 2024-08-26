package com.cob.billing.entity.bill.fee.schedule;

import com.cob.billing.model.bill.fee.schedule.FeeScheduleLineModel;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.model.clinical.provider.SimpleProvider;
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
@Table(name = "fee_schedule")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Setter
@Getter
public class FeeScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fee_Schedule_name")
    private String name;
    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "default_fee")
    private Boolean defaultFee;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "fee_schedule_line", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<FeeScheduleLineModel> feeLines;

    @Column(name = "provider", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private SimpleProvider provider;

    @Column(name = "insurance", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private InsuranceCompanyHolder insurance;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }
}
