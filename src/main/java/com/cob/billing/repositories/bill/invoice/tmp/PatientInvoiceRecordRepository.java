package com.cob.billing.repositories.bill.invoice.tmp;

import com.cob.billing.entity.bill.invoice.tmp.PatientInvoiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientInvoiceRecordRepository extends JpaRepository<PatientInvoiceRecord, Long> {
    Optional<PatientInvoiceRecord> findBySubmissionId(Long submissionId);
}
