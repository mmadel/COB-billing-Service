package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceDetailsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientInvoiceDetailsRepository extends CrudRepository<PatientInvoiceDetailsEntity,Long> {

    @Query(" SELECT pind FROM PatientInvoiceDetailsEntity pind INNER JOIN FETCH pind.patientInvoice pin " +
            "WHERE (pind.patientSession.status='Submit' OR pind.patientSession='Partial') " +
            "AND pind.serviceLine.type ='Invoice' " +
            "AND JSON_EXTRACT(pin.insuranceCompany, '$.id') = :insuranceCompany")
    List<PatientInvoiceDetailsEntity> findBySessionSubmittedByInsuranceCompany(@Param("insuranceCompany") Long insuranceCompany);
}
