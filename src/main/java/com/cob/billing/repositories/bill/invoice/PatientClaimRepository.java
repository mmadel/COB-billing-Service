package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.clinical.patient.claim.PatientClaimEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientClaimRepository extends CrudRepository<PatientClaimEntity,Long> {
    
    Optional<PatientClaimEntity> findDistinctByPatientInvoice_Id(Long id);
}
