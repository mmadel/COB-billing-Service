package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceDetailsEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientInvoiceDetailsRepository extends CrudRepository<PatientInvoiceDetailsEntity,Long> {

    @Query(" SELECT pind FROM PatientInvoiceDetailsEntity pind INNER JOIN FETCH pind.patientInvoice pin " +
            "WHERE (pind.patientSession.status='Submit' OR pind.patientSession.status='Partial') " +
            "AND pind.serviceLine.type ='Invoice' " +
            "AND FUNCTION('jsonb_extract_path_text',pin.insuranceCompany, 'id') = :insuranceCompany")
    List<PatientInvoiceDetailsEntity> findBySessionSubmittedByInsuranceCompany(@Param("insuranceCompany") String insuranceCompany);
}
