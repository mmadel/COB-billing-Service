package com.cob.billing.repositories.bill.era;

import com.cob.billing.entity.bill.era.ClaimAdjustmentReasonCodeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClaimAdjustmentReasonCodeRepository extends CrudRepository<ClaimAdjustmentReasonCodeEntity, Long> {
    Optional<ClaimAdjustmentReasonCodeEntity> findByCode(String code);

    @Query("Select carc from ClaimAdjustmentReasonCodeEntity carc where carc.code in :codes")
    Optional<List<ClaimAdjustmentReasonCodeEntity>> findByCodes(@Param("codes") List<String> codes);

}
