package com.cob.billing.repositories.bill.invoice.tmp;

import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientSubmittedClaimRepository extends CrudRepository<PatientSubmittedClaim, Long> {
    List<PatientSubmittedClaim> findBySubmissionStatus(@Param("status") SubmissionStatus status);

    @Modifying
    @Query("update PatientSubmittedClaim pc SET  pc.submissionStatus =:submissionStatus WHERE pc.id IN :ids")
    void updateSubmittedClaimStatus(@Param("submissionStatus") SubmissionStatus submissionStatus, @Param("ids") List<Long> ids);

    @Query("select pc  from  PatientSubmittedClaim pc where pc.id IN :ids")
    List<PatientSubmittedClaim> test(@Param("ids") List<Long> ids);


    @Query("SELECT psc from PatientSubmittedClaim psc INNER JOIN FETCH psc.patientInvoiceRecord pir " +
            "WHERE  pir.insuranceCompanyId = :insuranceCompany")
    List<PatientSubmittedClaim> findBySessionSubmittedByInsuranceCompany(@Param("insuranceCompany") Long insuranceCompany);

    @Query("SELECT psc from PatientSubmittedClaim psc WHERE psc.patientInvoiceRecord.submissionId =:submissionId")
    List<PatientSubmittedClaim> findBySubmissionId(@Param("submissionId") Long submissionId);

}
