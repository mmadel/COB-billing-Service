package com.cob.billing.usecases.clinical.patient.auth;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.auth.PatientAuthorization;
import com.cob.billing.repositories.clinical.PatientAuthorizationRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FetchPatientAuthorizationUseCase {
    @Autowired
    PatientAuthorizationRepository patientAuthorizationRepository;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientAuthorization> find(Long patientId, Long sessionId) {
        List<PatientAuthorization> patientAuthorizations = patientAuthorizationRepository.findByPatient_Id(patientId).get().stream()
                .map(patientAuthorizationEntity -> {
                    PatientAuthorization patientAuthorization = mapper.map(patientAuthorizationEntity, PatientAuthorization.class);
                    String[] insCompany = {patientAuthorizationEntity.getPatientInsuranceCompanyName(),patientAuthorizationEntity.getPatientInsuranceCompany().toString()};
                    patientAuthorization.setInsCompany(insCompany);
                    patientAuthorization.setIsExpired(checkExpiration(patientAuthorizationEntity.getExpireDateNumber()));
                    if (sessionId != null && patientAuthorizationEntity.getSession()!= null){
                        Optional<PatientSessionEntity>   patientSessionEntityOptional =  patientAuthorizationEntity.getSession().stream().filter(patientSessionEntity -> patientSessionEntity.getId().equals(sessionId)).findFirst();
                        patientAuthorization.setSelected(patientSessionEntityOptional.isPresent());
                    }
                    return patientAuthorization;
                })
                .collect(Collectors.toList());
        return patientAuthorizations;
    }

    private boolean checkExpiration(Long authExpireDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() > authExpireDate;
    }
}
