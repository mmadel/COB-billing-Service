package com.cob.billing.repositories.clinical.insurance.company;

import com.cob.billing.entity.clinical.insurance.compnay.PatientInsuranceExternalCompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientInsuranceExternalCompanyRepository extends CrudRepository<PatientInsuranceExternalCompanyEntity,Long> {
    Optional<PatientInsuranceExternalCompanyEntity> deleteByExternalPatientInsurance_Id(Long id);
}
