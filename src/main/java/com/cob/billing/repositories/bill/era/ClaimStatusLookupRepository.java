package com.cob.billing.repositories.bill.era;

import com.cob.billing.entity.bill.era.ClaimStatusLookupEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClaimStatusLookupRepository extends CrudRepository<ClaimStatusLookupEntity,Long> {
    Optional<ClaimStatusLookupEntity> findByStatusId(String statusId);
}
