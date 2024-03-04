package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientCaseRepository extends JpaRepository<PatientCaseEntity, Long> {
    @Query("SELECT pc FROM PatientCaseEntity pc where pc.patient.id =:patientId")
    List<PatientCaseEntity> findPatientCases(@Param("patientId") Long patientId);
}
