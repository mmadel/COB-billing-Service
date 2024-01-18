package com.cob.billing.repositories.admin;

import com.cob.billing.entity.admin.ClinicEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClinicRepository extends PagingAndSortingRepository<ClinicEntity,Long> {
    Optional<ClinicEntity> findByTitle(String title);
}
