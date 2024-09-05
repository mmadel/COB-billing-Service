package com.cob.billing.repositories.bill.era;

import com.cob.billing.entity.bill.era.ERAHistoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface ERAHistoryRepository extends CrudRepository<ERAHistoryEntity, Long> {
}
