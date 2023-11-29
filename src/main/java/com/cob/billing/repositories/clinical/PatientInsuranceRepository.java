package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientInsuranceRepository extends JpaRepository<PatientInsuranceEntity, Long> {
}
