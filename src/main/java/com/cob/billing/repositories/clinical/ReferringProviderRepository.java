package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReferringProviderRepository extends PagingAndSortingRepository<ReferringProviderEntity, Long> {
}
