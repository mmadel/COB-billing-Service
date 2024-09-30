package com.cob.billing.repositories.clinical;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {

    @Query("SELECT pe FROM PatientEntity pe where " +
            "LOWER(pe.firstName) LIKE CONCAT('%',:name,'%') OR LOWER(pe.middleName) LIKE CONCAT('%',:name,'%')  OR LOWER(pe.lastName) LIKE CONCAT('%',:name,'%')")
    List<PatientEntity> findByName(@Param("name") String name);

    PatientEntity findByFirstNameAndLastName(String firstName, String lastName);

    @Modifying
    @Query("update PatientEntity p set  p.authorizationWatching = false where p.id = :patientId")
    void turnOffAuthorization(@Param("patientId") Long patientId );
    @Modifying
    @Query("update PatientEntity p set  p.authorizationWatching = true where p.id = :patientId")
    void turnOnAuthorization(@Param("patientId") Long patientId);


    @Query("SELECT p FROM PatientEntity p LEFT JOIN PatientInsuranceEntity pin ON pin.patient = p   WHERE " +
            "((:name is null or upper(p.lastName) LIKE CONCAT('%',:name,'%')) OR  (:name is null or upper(p.firstName) LIKE CONCAT('%',:name,'%')) ) " +
            "AND (:phone is null or p.phone LIKE CONCAT('%',:phone,'%'))" +
            "AND (:email is null or p.email LIKE CONCAT('%',:email,'%'))" +
            "AND (:insuranceCompany is null or upper(pin.patientInsuranceExternalCompany.insuranceCompany.name) LIKE CONCAT('%',:insuranceCompany,'%'))")
    Page<PatientEntity> findFilter( Pageable pageable,@Param("name") String name,@Param("phone") String phone,@Param("email") String email,@Param("insuranceCompany") String insuranceCompany);

}
