package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.clinical.patient.claim.PatientClaimEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientClaimRepository extends CrudRepository<PatientClaimEntity,Long> {
}
