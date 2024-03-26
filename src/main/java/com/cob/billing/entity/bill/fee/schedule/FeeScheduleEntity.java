package com.cob.billing.entity.bill.fee.schedule;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "fee_schedule")
@Setter
@Getter
public class FeeScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "default_fee")
    private Boolean defaultFee;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "feeSchedule")
    private List<FeeScheduleLineEntity> feeLines;

    @PrePersist
    private void setCreatedDate() {
        createdAt = new Date().getTime();
    }
}
