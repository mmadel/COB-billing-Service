package com.cob.billing.repositories.clinical.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.PatientInsuranceInternalCompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientInsuranceInternalCompanyRepository extends CrudRepository<PatientInsuranceInternalCompanyEntity,Long> {
    Optional<PatientInsuranceInternalCompanyEntity> deleteByPatientInsuranceId(Long id);
}
