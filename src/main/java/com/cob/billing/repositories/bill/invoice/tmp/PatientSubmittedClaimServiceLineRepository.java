package com.cob.billing.repositories.bill.invoice.tmp;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaimServiceLine;
import org.springframework.data.repository.CrudRepository;

public interface PatientSubmittedClaimServiceLineRepository extends CrudRepository<PatientSubmittedClaimServiceLine, Long> {
}
