package com.cob.billing.repositories.bill.invoice.tmp;

import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import org.springframework.data.repository.CrudRepository;

public interface PatientSubmittedClaimRepository extends CrudRepository<PatientSubmittedClaim,Long> {
}
