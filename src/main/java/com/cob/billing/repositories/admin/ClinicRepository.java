package com.cob.billing.repositories.admin;

import com.cob.billing.entity.admin.ClinicEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClinicRepository extends PagingAndSortingRepository<ClinicEntity,Long> {
}
