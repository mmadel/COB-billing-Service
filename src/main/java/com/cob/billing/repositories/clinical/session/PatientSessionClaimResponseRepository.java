package com.cob.billing.repositories.clinical.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionClaimResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientSessionClaimResponseRepository extends JpaRepository<PatientSessionClaimResponseEntity,Long> {
}
