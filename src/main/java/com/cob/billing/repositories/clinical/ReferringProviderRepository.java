package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ReferringProviderRepository extends PagingAndSortingRepository<ReferringProviderEntity, Long> {
    Optional<ReferringProviderEntity> findByNpi(String npi);
}
