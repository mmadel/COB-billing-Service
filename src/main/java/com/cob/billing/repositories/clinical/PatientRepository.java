package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {

    @Query("SELECT pe FROM PatientEntity pe where " +
            "pe.firstName LIKE CONCAT('%',:name,'%') OR pe.middleName LIKE CONCAT('%',:name,'%')  OR pe.lastName LIKE CONCAT('%',:name,'%')")
    List<PatientEntity> findByName(@Param("name") String name);

    PatientEntity findByFirstNameAndLastName(String firstName, String lastName);
}
