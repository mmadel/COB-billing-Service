package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientCaseRepository extends JpaRepository<PatientCaseEntity, Long> {
}
