package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientInsuranceRepository extends JpaRepository<PatientInsuranceEntity, Long> {
      @Query(value = "SELECT pi FROM PatientInsuranceEntity pi where pi.patient.id =:patientId order by pi.createdAt desc")
      List<PatientInsuranceEntity> find(@Param("patientId") Long patientId);
}
