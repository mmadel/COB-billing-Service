package com.cob.billing.repositories.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientInvoiceRepository extends CrudRepository<PatientInvoiceEntity,Long> {

    @Query("SELECT pinv.patient FROM PatientInvoiceEntity pinv where pinv.insuranceCompany =:id")
    List<PatientEntity> findPatientsByInsuranceCompany(@Param("id") Long id);

    @Query("SELECT  pinv FROM PatientInvoiceEntity pinv where pinv.insuranceCompany =:insuranceCompany")
    List<PatientInvoiceEntity> findBySessionSubmittedByInsuranceCompany(@Param("insuranceCompany") Long insuranceCompany);

}
