package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientInvoiceRepository extends CrudRepository<PatientInvoiceEntity,Long> {

}
