package com.cob.billing.repositories.bill.fee.schedule;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FeeScheduleRepository extends CrudRepository<FeeScheduleEntity,Long> {
    @Query("Select fs from FeeScheduleEntity fs where fs.defaultFee = true")
    Optional<FeeScheduleEntity> findDefaultFee();

    @Query("Select fs from FeeScheduleEntity fs " +
            "where JSON_EXTRACT(fs.provider, '$.npi') = :npi " +
            "AND fs.active = true")
    Optional<FeeScheduleEntity> findFeeScheduleByProvider(@Param("npi") String npi);


    @Query("Select fs from FeeScheduleEntity fs " +
            "where JSON_EXTRACT(fs.insurance, '$.id') =:id " +
            "AND fs.active = true")
    Optional<FeeScheduleEntity> findFeeScheduleByInsurance(@Param("id") Long id);
}
