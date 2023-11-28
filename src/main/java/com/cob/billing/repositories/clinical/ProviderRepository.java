package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.provider.ProviderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProviderRepository extends PagingAndSortingRepository<ProviderEntity, Long> {
}
